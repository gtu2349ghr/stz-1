package com.bjpowernode.p2p.server.impl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.loan.RefundMapper;
import com.bjpowernode.p2p.model.Refund;
import com.bjpowernode.p2p.server.user.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Service(interfaceClass = RefundService.class,version = "1.0.0",timeout = 15000)
public class RefundServiceImpl implements RefundService {
@Resource
    RefundMapper refundMapper;
    @Override
    /**
     * 创建退款订单
     */
    public int insertFound(Refund refund) {
        System.out.println("开始执行退款订单mapper");
        return refundMapper.insert(refund);
    }

    @Override
    public Refund selectByOrderNo(String orderNo) {
        System.out.println("根据订单查询全部接口");
        return refundMapper.selectByOrderNo(orderNo);
    }
}
