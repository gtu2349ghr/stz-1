package com.bjpowernode.Controller;

import com.bjpowernode.Config.ApplicationContextUtil;
import com.bjpowernode.Config.WxPayConfig;
import com.bjpowernode.Vo.Result;
import com.bjpowernode.p2p.mapper.loan.RefundMapper;
import com.bjpowernode.p2p.server.user.RefundService;
import com.bjpowernode.srevice.Refundservice;
import com.bjpowernode.utils.HttpUtils;
import com.google.gson.Gson;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.exception.ParseException;
import com.wechat.pay.contrib.apache.httpclient.exception.ValidationException;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("api/wx-refund")
@CrossOrigin
public class RefundControler {
    @Autowired
   private Refundservice refundService;
    @Autowired
   private WxPayConfig wxPayConfig;
    @Autowired
    private Verifier PayVerfire;//拿到证书实例
    @RequestMapping("refundOrderNo")
    public Result RefundOrder(@RequestParam("orderNo") String orderNo){
        System.out.println(orderNo);
         refundService.refundApply(orderNo);
        return Result.ok();
    }
    @SneakyThrows
    @RequestMapping("refund")
    public  String  notifyRefund(HttpServletRequest request, HttpServletResponse response) throws ValidationException, ParseException {
        Gson gson = new Gson();
        //先获得请求，从HTTPUtist中获取通知体
        String requestBody = HttpUtils.readData(request);
        //然后将通知体转化为map
        HashMap bodyMap = gson.fromJson(requestBody, HashMap.class);
        // 构建request，传入必要参数
        //平台证书序列号是从证书实例里面拿到
        String wechatPaySerial = PayVerfire.getValidCertificate().getSerialNumber().toString(16);
        //然后里面这些都是从请求头里拿的
        NotificationRequest request1 = new NotificationRequest.Builder().withSerialNumber(wechatPaySerial)
                .withNonce(  request.getHeader("Wechatpay-Nonce"))
                .withTimestamp(request.getHeader("Wechatpay-Timestamp"))
                .withSignature(request.getHeader("Wechatpay-Signature"))
                .withBody(requestBody)
                .build();
        //下面两个参数平台证书，私钥，
        NotificationHandler handler = new NotificationHandler(PayVerfire, wxPayConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));
        // 验签和解析请求体
        Notification notification = handler.parse(request1);
       //从notificaion中拿到解密后的请求体，然后转化为json
        System.out.println(notification.getDecryptData());
        String decryptData = notification.getDecryptData();
        HashMap hashMap = gson.fromJson(decryptData, HashMap.class);
        //然后得到通知信息
        HashMap<String, Object> map = new HashMap<>();
        if(hashMap.get("refund_status").equals("SUCCESS")){
            //成功之后拿到退款单号写入
            String outRefundNo =(String) hashMap.get("out_refund_no");
         //拿到订单号
            String outTradeNo = (String) hashMap.get("out_trade_no");

            RefundMapper refundMapper= ApplicationContextUtil.getBean(RefundMapper.class);
            int i = refundMapper.updateByoutOrderNo(outTradeNo,outRefundNo);
            if(i==1){
                log.info("导入退款单成功");
            }
            log.info("退款成功");
            response.setStatus(200);
            map.put("code","SUCCESS");
            map.put("message","成功");
            //返回gson
           return gson.toJson(map);
    }else {
            response.setStatus(500);
            map.put("code","ERROR");
            map.put("message","失败");
            return  gson.toJson(map);
        }
    }




}
