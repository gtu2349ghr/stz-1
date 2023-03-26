package com.financial.Config;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.Data;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;


@Configuration
@PropertySource("classpath:wxpay.properties") //读取配置文件
@ConfigurationProperties(prefix="wxpay") //读取wxpay节点
@Data //使用set方法将wxpay节点中的值填充到当前类的属性中
public class WxPayConfig {

    // 商户号
    private String mchId;

    // 商户API证书序列号
    private String mchSerialNo;

    // 商户私钥文件
    private String privateKeyPath;

    // APIv3密钥
    private String apiV3Key;

    // APPID
    private String appid;

    // 微信服务器地址
    private String domain;

    // 接收结果通知地址
    private String notifyDomain;

    /**
     * 获取商户的私钥
     * @param fileName
     * @return
     */
    private PrivateKey getPrivateKey(String fileName){
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            System.out.println(fileInputStream);
            PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(fileInputStream);
         return merchantPrivateKey;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("获取商户私钥失败路径错误",e);
        }
    }

    /**
     * 获取验签器
     * @return
     * @throws NotFoundException
     * @throws HttpCodeException
     * @throws GeneralSecurityException
     * @throws IOException
     */
    @Bean
    public  Verifier getPayVerfire() throws NotFoundException, HttpCodeException, GeneralSecurityException, IOException {
        // 获取证书管理器实例
        CertificatesManager  certificatesManager = CertificatesManager.getInstance();
// 向证书管理器增加需要自动更新平台证书的商户信息
        //添加了商户id，商户序列号，和商户私钥
        String privateKeyPath = getPrivateKeyPath();
        PrivateKey privateKey = getPrivateKey(privateKeyPath);
        //私钥签名对象
        PrivateKeySigner privateKeySigner = new PrivateKeySigner(mchSerialNo, privateKey);
        //身份认证对象
        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials(mchId, privateKeySigner);

        certificatesManager.putMerchant(mchId,wechatPay2Credentials , apiV3Key.getBytes(StandardCharsets.UTF_8));
        // 从证书管理器中获取verifier
        Verifier verifier = certificatesManager.getVerifier(mchId);
        return  verifier;
    }



    /**
     * 获取验签器和获取http请求
     * @return
     */
    @Bean
    public CloseableHttpClient getPayClient(Verifier verifier) throws IOException, GeneralSecurityException, HttpCodeException, NotFoundException {
        //添加了商户id，商户序列号，和商户私钥
        PrivateKey privateKey = getPrivateKey(getPrivateKeyPath());
        System.out.println(privateKey);
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, privateKey)
                .withValidator(new WechatPay2Validator(verifier));
        // ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
// 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
        CloseableHttpClient httpClient = builder.build();
        return  httpClient;
    }



}
