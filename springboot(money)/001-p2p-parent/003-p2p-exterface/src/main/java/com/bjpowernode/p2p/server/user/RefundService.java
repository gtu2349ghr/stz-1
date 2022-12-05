package com.bjpowernode.p2p.server.user;

import com.bjpowernode.p2p.model.Refund;

public interface RefundService {
    /**
     * 创建退款订单
     * @param refund
     * @return
     */
    int insertFound(Refund refund);
    /**
     * 根据订单号查询去全部
     */
    Refund selectByOrderNo(String orderNo);
}
