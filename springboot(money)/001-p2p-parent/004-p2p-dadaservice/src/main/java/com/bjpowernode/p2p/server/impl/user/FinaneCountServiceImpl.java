package com.bjpowernode.p2p.server.impl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.user.FinanceAccountMapper;
import com.bjpowernode.p2p.server.user.FinaneCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Service(interfaceClass = FinaneCountService.class,version = "1.0.0",timeout = 15000)
public class FinaneCountServiceImpl implements FinaneCountService {
    @Resource
    FinanceAccountMapper financeAccountMapper;
    @Override
    public Double querryCount(Integer uid) {
        Double aDouble = financeAccountMapper.selectByFinanceAccount(uid);
        return aDouble;
    }

    @Override
    /**
     * 账户充值成功之后更新 余额
     */
    public int updateFinanceRecord(Integer uid, Double rechargeMoney) {
        System.out.println("开始更新余额");
        return financeAccountMapper.updateRecord(uid,rechargeMoney);
    }

    @Override
    public Double selectSumMoney() {

        return     financeAccountMapper.selectSumMoney();
    }
}
