package com.financial.p2p.server.impl.loan;

import com.alibaba.dubbo.config.annotation.Service;
import com.financial.p2p.mapper.loan.RechargeRecordMapper;
import com.financial.p2p.model.ProductPage;
import com.financial.p2p.model.RechargeRecord;
import com.financial.p2p.server.loan.RechargerecordService;
import com.financial.p2p.server.user.FinaneCountService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Service(interfaceClass = RechargerecordService.class,version = "1.0.0",timeout = 15000)
public class RechargerecordServiceImpl implements RechargerecordService {
    @Resource
    RechargeRecordMapper rechargeRecordMapper;
    @Resource
     FinaneCountService finaneCountService;
    @Override
    public int rechagrgeRows(RechargeRecord rechargeRecord) {
        System.out.println("进入充值方法");
        return  rechargeRecordMapper.insert(rechargeRecord);
    }

    /**
     * 处理块钱返回的数据
     * @param orderId
     * @param payResult
     * @return
     */
    @Override
    public int doWithKQNotify(String orderId, Integer payResult) throws Exception {
        System.out.println("开始了处理数据");
        //1. 查询订单号是否商家订单
        RechargeRecord rechargeRecord=   rechargeRecordMapper.selectByOrderId(orderId);
        if(rechargeRecord==null){
            throw new Exception("根据订单号没有查到过结果");
        }
        //2. 判断订单是否处理过，没有处理过的才能处理
        System.out.println("订单没有处理过"+rechargeRecord.getRechargeStatus());
        String rechargeStatus = rechargeRecord.getRechargeStatus();
        System.out.println("这是订单的状态");
        if(rechargeStatus.equals("0")){
             //3. 更新账户余额
            System.out.println("开始更新余额");
             int rows=  finaneCountService.updateFinanceRecord(rechargeRecord.getUid(),rechargeRecord.getRechargeMoney());
            if(rows==0){
             throw new Exception("更新账户余额失败");
            }
             System.out.println("更新余额成功");
             //4. 修改充值表中的充值状态
             rechargeRecord.setRechargeStatus("1");
             int i = rechargeRecordMapper.updateByPrimaryKey(rechargeRecord);
             if(i==1){
                 System.out.println("修改状态成功");
             }
         }

        return 1;
    }

    @Override
    public int cheakOrderNo1(String orderNo) {
        System.out.println(orderNo);
        System.out.println("进入mapper");
        RechargeRecord rechargeRecord = rechargeRecordMapper.selectByOrderId(orderNo);
        System.out.println(rechargeRecord);
        if(rechargeRecord.getRechargeStatus().equals("1")){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public List<RechargeRecord> cheackOrderin() {
        List<RechargeRecord> list = rechargeRecordMapper.cheackOrderin();
        System.out.println("这是查到的集合");
        System.out.println(list);
        return list;
    }

    /**
     * 查询5条充值记录
     * @param rows
     */
    @Override
    public List<RechargeRecord> selectAllRecord(int rows,int uid) {
      return  rechargeRecordMapper.selectByuid(rows,uid);
    }

    @Override
    public ProductPage<RechargeRecord> selectRechargeLimit(ProductPage<RechargeRecord> page,int uid) {
         //进入mapper
        Integer count = page.getCount();
        Integer currentPage = page.getCurrentPage();
        List<RechargeRecord> list=  rechargeRecordMapper.selectByPage(count,currentPage,uid);
      //拿到list
        page.setProductList(list);
        //然后查总数量
       int AllPage= rechargeRecordMapper.selectBycount(uid);
       //根据总数量计算总页数
        int totalPage=AllPage/(page.getCount());
        int mod=AllPage%(page.getCount());
        if(mod>0){
            totalPage=totalPage+1;
        }
        //然后写入
        page.setAllPage(AllPage);//写入总数量
        page.setTotalPage(totalPage);//写入总页数
        return page;
    }

}
