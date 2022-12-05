package com.bjpowernode.p2p.server.impl.loan;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.loan.BidInfoMapper;
import com.bjpowernode.p2p.mapper.loan.LoanInfoMapper;
import com.bjpowernode.p2p.mapper.user.FinanceAccountMapper;
import com.bjpowernode.p2p.model.BidInfo;
import com.bjpowernode.p2p.model.LoanInfo;
import com.bjpowernode.p2p.model.ProductPage;
import com.bjpowernode.p2p.server.loan.LoanInfoService;
import com.bjpowernode.springboot.cons.Constans;
import com.bjpowernode.springboot.cons.utils.Result;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = LoanInfoService.class,version = "1.0.0",timeout = 15000)
public class loanInfoServiceImpl implements LoanInfoService {
    @Resource
      LoanInfoMapper loanInfoMapper;
    @Resource
     BidInfoMapper bidInfoMapper;
    @Resource
     FinanceAccountMapper financeAccountMapper;
    @Resource
     RedisTemplate redisTemplate;
    public Double  queryHistryAvgRate() {
        //设置redis中key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //先查redis
        Double histryAvgRate = (Double)redisTemplate.opsForValue().get(Constans.HISTRY_AVG_RATE);
        if(!ObjectUtils.allNotNull(histryAvgRate)){
            //没有查到进入线程锁
            synchronized (this){
                //当新的线程进入之后再查一边有了就不查了
                histryAvgRate = (Double)redisTemplate.opsForValue().get(Constans.HISTRY_AVG_RATE);
                if(!ObjectUtils.allNotNull(histryAvgRate)){
                    //没有就查收数据库然后给redis
                    histryAvgRate = loanInfoMapper.selectHistryAvgRate();
                    redisTemplate.opsForValue().set(Constans.HISTRY_AVG_RATE,histryAvgRate,7, TimeUnit.DAYS);
                }
            }
         }
        return histryAvgRate;
    }

    @Override
    public List<LoanInfo> querryProduceType_X(Map<String, Object> params) {
        List<LoanInfo> loanInfos = loanInfoMapper.selectByProductType_X(params);
        return loanInfos;
    }

    @Override
    public List<LoanInfo> querryProduceType_Y(Map<String, Object> params) {

        return loanInfoMapper.selectByProductType_Y(params);
    }

    @Override
    public List<LoanInfo> querryProduceType_S(Map<String, Object> params) {

        return loanInfoMapper.selectByProductType_S(params);
    }

    @Override
    public ProductPage<LoanInfo> querryProductPage(Map<String, Object> params) {

        ProductPage<LoanInfo> obj = new ProductPage<>();
    int pageSize=9;

        Integer count = loanInfoMapper.selectPage(params);
        int totalPage=count/pageSize;
        int mod=count%pageSize;
        if(mod>0){
            totalPage=totalPage+1;
        }
          obj.setAllPage(totalPage);
        obj.setCount(count);
        params.put("pageSize",pageSize);
        List<LoanInfo> loanInfos = loanInfoMapper.selectProductList(params);
        obj.setProductList(loanInfos);
        return obj;
    }

    @Override
    public LoanInfo querryLoanInfoById(Integer id) {
        LoanInfo loanInfo=loanInfoMapper.selectByPrimaryKey(id);


        return loanInfo;
    }

    @Override
    public Result invest(HashMap<String, Object> map) {
        //更新一条投资记录
        Integer loanId = (Integer) map.get("loanId");
        Double bidMoney =(Double)map.get("bidMoney");
        Integer uid = (Integer) map.get("uid");
        String phone = (String) map.get("phone");
        BidInfo bidInfo = new BidInfo();
        bidInfo.setBidMoney(bidMoney);
        bidInfo.setBidTime(new Date());
        bidInfo.setLoanId(loanId);
        bidInfo.setUid(uid);
        bidInfo.setBidStatus(1);
        int insert = bidInfoMapper.insertSelective(bidInfo);
        if(insert==0){//新增失败
            return Result.erro("投资失败，投资表数据更新失败");
        }
        //更新剩余可投金额
        int update=loanInfoMapper.updateLeftProductMoney(map);
        if(update==0){
            return Result.erro("投资失败，修改账户剩余金额记录错误");
        }
        //如果剩余磕头金额为0的话，更新满标状态，更新满标时间
        LoanInfo loanInfo = loanInfoMapper.selectByPrimaryKey(loanId);
        if(loanInfo.getLeftProductMoney()==0){
            //更新满标状态
           //更新满标时间
            loanInfo.setProductStatus(1);
            loanInfo.setProductFullTime(new Date());
           int rows= loanInfoMapper.updateByPrimaryKey(loanInfo);
           if(rows==0){
               return Result.erro("投资失败，更新满标状态失败");
           }
        }
        //更新账户余额
       int row= financeAccountMapper.updaeAccountMoney(uid,bidMoney);
        if(row==0){
            return Result.erro("投资失败，更新账户余额失败");
        }
        //生成投资排行榜
        redisTemplate.opsForZSet().incrementScore(Constans.INVEST_TOP,phone,bidMoney);
        return Result.success("投资成功");
    }

    /**
     * 查询已满标产品的信息
     * @return
     */
    @Override
    public List<LoanInfo> querryLoanInfoByproductStas() {

        return  loanInfoMapper.seleceByLoanInfoByProductStas();
    }
}
