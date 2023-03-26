package com.financial.p2p.mapper.loan;

import com.financial.p2p.model.Refund;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefundMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Refund refund);

    Refund selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Refund refund);

    /**
     * 跟据订单查询全部
     * @param orderNo
     * @return
     */
    Refund selectByOrderNo(String orderNo);

    /**
     * 查询5条充值记录
     * @param outTradeNo
     * @param outRefundNo
     * @return
     */
    int updateByoutOrderNo(String outTradeNo,String outRefundNo);
}
