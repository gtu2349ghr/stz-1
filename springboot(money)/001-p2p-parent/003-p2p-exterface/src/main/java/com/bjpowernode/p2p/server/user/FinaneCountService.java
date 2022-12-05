package com.bjpowernode.p2p.server.user;

public interface FinaneCountService {
    Double querryCount(Integer uid);

    /**
     * 充值成功之后更新账户余额
     * @param uid
     * @param rechargeMoney
     * @return
     */
    int updateFinanceRecord(Integer uid, Double rechargeMoney);

    /**
     * 查询投资总金额
     * @return
     */
    Double selectSumMoney();
}

