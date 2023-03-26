package com.financial.p2p.server.impl.loan;

import com.alibaba.dubbo.config.annotation.Service;
import com.financial.p2p.mapper.loan.IncomeRecordMapper;
import com.financial.p2p.mapper.user.FinanceAccountMapper;
import com.financial.p2p.model.BidInfo;
import com.financial.p2p.model.IncomeRecord;
import com.financial.p2p.model.LoanInfo;
import com.financial.p2p.server.loan.BidInfoService;
import com.financial.p2p.server.loan.IncomeRecoredService;
import com.financial.p2p.server.loan.LoanInfoService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 获取收益计划
 */
@Component
@Service(interfaceClass = IncomeRecoredService.class,version = "1.0.0",timeout = 15000)
public class IncomeRecoredServiceImpl implements IncomeRecoredService {
    @Resource
    IncomeRecordMapper incomeRecordMapper;
    @Resource
    LoanInfoService loanInfoService;
    @Resource
    BidInfoService bidInfoService;
    @Resource
    FinanceAccountMapper financeAccountMapper;
    @SneakyThrows
    @Override
    public int generateIncomes()  {
        //先查询遍历产品信息表，（已满表的产品）拿到产品标识
        List<LoanInfo> loanInfoList = loanInfoService.querryLoanInfoByproductStas();
        //先定义返回值，默认失败
        int row=0;
        for (LoanInfo loaninfo : loanInfoList) {
            //然后遍历投资信息表
            List<BidInfo> bidInfo = bidInfoService.querryBidInfoById(loaninfo.getId());
            for (BidInfo bidInfo1 : bidInfo) {
                //生成收益计划
                IncomeRecord incomeRecord = new IncomeRecord();
                incomeRecord.setUid(bidInfo1.getUid());
                incomeRecord.setBidId(bidInfo1.getId());
                incomeRecord.setLoanId(loaninfo.getId());
                //投资金额
                incomeRecord.setBidMoney(bidInfo1.getBidMoney());
                //设置收益状态（因为还没有添加进本金，所以未返还为0）
                incomeRecord.setIncomeStatus(0);
                //生成收益时间 公式：满标时间加上它的投资周期，这里因为新手宝的是天，而其他的是月，所以做一个区分
                //而因为数字和日期不能相加，所以要用lang3的工具类
                Date incomeDate = null;
                Double incomeMoney = 0.0;
                if (loaninfo.getProductType() == 0) {
                    //生成日期
                    //新手宝
                    incomeDate = DateUtils.addDays(loaninfo.getProductFullTime(), loaninfo.getCycle());
                    //生成收益
                    //公式：收益等于：本金*日利率*周期（天）
                    incomeMoney = bidInfo1.getBidMoney() * (loaninfo.getRate() / 100 / 360) * loaninfo.getCycle();
                } else {
                    //收益时间
                    //优选和散标
                    incomeDate = DateUtils.addMonths(loaninfo.getProductFullTime(), loaninfo.getCycle());
                    //生成收益
                    //公式：收益等于：本金*日利率*周期（月）
                    incomeMoney = bidInfo1.getBidMoney() * (loaninfo.getRate() / 100 / 360) * loaninfo.getCycle() * 30;
                }
                incomeMoney = Math.round(incomeMoney * Math.pow(10, 2)) / Math.pow(10, 2);
                incomeRecord.setIncomeMoney(incomeMoney);
                incomeRecord.setIncomeDate(incomeDate);
                //然后添加记录进入收益表里面
                int rows = incomeRecordMapper.insert(incomeRecord);
                //将结果添加进标记中
                row=rows;
            }
        }
        return row;
    }
    @Override
    public int incomeRecoredBack() throws Exception {
        int rows=0;
        //先查询收益记录,根据时间查询，因为投资有周期，所以当收益是当天的话才返还
        List<IncomeRecord>  incomeRecord =incomeRecordMapper.selectIncomeBackMoney();
        //遍历待返还的记录
        for (IncomeRecord record : incomeRecord) {
            //然后将投资金额和收益返还到用户金额中
            Double incomeBack=record.getIncomeMoney()+record.getBidMoney();
            int uid=record.getUid();
            int row=   financeAccountMapper.updateIncomeBackMoney(uid,incomeBack);
            rows=row;
            if(rows==1){
                //然后将状态改为已返还
                record.setIncomeStatus(1);
                int i = incomeRecordMapper.updateByPrimaryKey(record);
                if(i==0){
                    throw new Exception("更新状态错误");
                }
                return rows;
            }else {
                throw new Exception("返还收益失败");
            }

        }
        return rows;
    }

    /**
     * 查询5条收益记录
     * @param rows
     * @param uid
     * @return
     */
    @Override
    public List<IncomeRecord> selectAllByLoanInfo(int rows, Integer uid) {
        return incomeRecordMapper.selectIncomByLoanInfo(rows,uid);
    }

}
