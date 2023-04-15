package com.pay.demo;

import com.pay.demo.Config.WxPayConfig;
import org.json.JSONString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.PrivateKey;

@SpringBootTest
class P2pPayDemoApplicationTests {
@Autowired
    private  WxPayConfig wxPayConfig;
    @Test
    void testGetwxConfig() {
//        String privateKeyPath = wxPayConfig.getPrivateKeyPath();
//        PrivateKey privateKey = wxPayConfig.getPrivateKey(privateKeyPath);
//        System.out.println(privateKey);
    }

}
