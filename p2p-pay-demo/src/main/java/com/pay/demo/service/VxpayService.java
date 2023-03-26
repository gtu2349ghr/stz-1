package com.pay.demo.service;

import java.util.Map;

public interface VxpayService {
    Map<String, Object> payOrder(Long orderid);
}
