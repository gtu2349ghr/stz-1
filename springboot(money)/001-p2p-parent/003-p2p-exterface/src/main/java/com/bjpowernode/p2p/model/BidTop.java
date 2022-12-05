package com.bjpowernode.p2p.model;

import java.io.Serializable;

public class BidTop  implements Serializable {
    private  String phone;
    private  Double bidMoney;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(Double bidMoney) {
        this.bidMoney = bidMoney;
    }

    public BidTop() {
    }

    public BidTop(String phone, Double bidMoney) {
        this.phone = phone;
        this.bidMoney = bidMoney;
    }
}
