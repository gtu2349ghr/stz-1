package com.bjpowernode.srevice;
import java.io.IOException;
import java.util.Map;

public interface WxpayService {
    Map<String, Object> payOrder(Double money);

    String querryOrderNo(String orderNo) throws IOException;


}
