package com.bjpowernode.p2p.model;

import lombok.Data;

@Data
public class Refund {
    private int id;
    private int uid;
    private String transactionId;
    private String outTradeNo;
    private String outRefundNo;
    private int refund;
    private int total;
    private String currency;
}
