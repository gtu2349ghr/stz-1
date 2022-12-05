package com.bjpowernode.p2p.server.loan;

import com.bjpowernode.p2p.model.ProductPage;
import com.bjpowernode.p2p.model.RechargeRecord;

import java.io.IOException;
import java.util.List;

public interface RechargerecordService {
    /**
     * 生成充值记录
     * @param rechargeRecord
     * @return
     */
    int rechagrgeRows(RechargeRecord rechargeRecord);
    /**
     * 处理数据
     */
    int doWithKQNotify(String orderId, Integer payResult) throws Exception;
    /**
     * 定时查单功能
     */
    int cheakOrderNo1(String orderNo);

    /**
     * 主动查单
     * @return
     */
    List<RechargeRecord> cheackOrderin();

    /**
     * 查询全部记录
     */
    List<RechargeRecord> selectAllRecord(int rows,int uid);

    /**
     * 分页查询充值记录
     * @param page
     * @return
     */
    ProductPage<RechargeRecord> selectRechargeLimit(ProductPage<RechargeRecord> page,int uid);
}
