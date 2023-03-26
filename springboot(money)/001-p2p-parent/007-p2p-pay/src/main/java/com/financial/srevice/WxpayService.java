package com.financial.srevice;
import com.financial.p2p.model.User;

import java.io.IOException;
import java.util.Map;

public interface WxpayService {
    Map<String, Object> payOrder(Double money, User user);

    int dowithOrder(String orderNo);
    String querryOrderNo(String orderNo) throws IOException;
    /**
     * 主动调用查单接口
     * @return
     */
    void cheackOrderin() throws Exception;


}
