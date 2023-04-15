package com.pay.demo.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pay.demo.Config.WxPayConfig;
import com.pay.demo.Vo.Result;
import com.pay.demo.service.VxpayService;
import com.pay.demo.utils.HttpUtils;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.Map;
@CrossOrigin
@Api(tags = "商品下单")
@RestController
@Slf4j
@RequestMapping("/api/wx-pay")
public class WxPayController {
    @Autowired
   private VxpayService vxpayService;
    //这里注入验签器。获得平台证书
    @Autowired
    private Verifier PayVerfire;
    @Autowired
    WxPayConfig wxPayConfig;
    @ApiOperation("调用api生成二维码")
    @RequestMapping("native")
    public Result Vxpay(@RequestParam(value = "productid")Long productid){
        //这里调用service
        //下单功能，返回二维码连接和订单号
        Map<String,Object> map= vxpayService.payOrder(productid);
        log.info("发起支付");
        return Result.ok().setData(map);
    }

    /**
     * 对结果进行应答
     * @param request
     * @param response
     * @return
     */
    @SneakyThrows
    @RequestMapping("")
    public  String novxpay(HttpServletRequest request, HttpServletResponse response){
        HashMap<String, Object> map = null;
        try {
            //应答的结果map集合
            map = new HashMap<>();
            //gson工具转化为json返回
            Gson gson = new Gson();
            //拿到得到的通知结果，这里用获取通知的工具类
            String requestBody= HttpUtils.readData(request);
            //将他转化为map
            HashMap bodyMap = gson.fromJson(requestBody, HashMap.class);
            String requestId = (String) bodyMap.get("id");
            log.info("支付通知的id",requestId);
            //验证签名
// 构建request，传入必要参数
            //从请求头当中获取参数
            String nonce = request.getHeader("Wechatpay-Nonce");
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String signature = request.getHeader("Wechatpay-Signature");
            //从验签器的方法里获取平台证书
            String wechatPaySerial = PayVerfire.getValidCertificate().getSerialNumber().toString(16);
            String apiV3Key = wxPayConfig.getApiV3Key();
            NotificationRequest request1 = new NotificationRequest.Builder().withSerialNumber(wechatPaySerial)
                    .withNonce(nonce)
                    .withTimestamp(timestamp)
                    .withSignature(signature)
                    .withBody(requestBody)
                    .build();
            NotificationHandler handler = new NotificationHandler(PayVerfire, apiV3Key.getBytes(StandardCharsets.UTF_8));
// 验签和解析请求体
            Notification notification = handler.parse(request1);
// 从notification中获取解密报文
            System.out.println(notification.getDecryptData());
            String decryptData = notification.getDecryptData();
            //这样就解密成功了，然后将解密后的报文转化为map形式提取数据
            HashMap hashMap = gson.fromJson(decryptData, HashMap.class);
              //到这里的时候因为商户如果5秒内没返回的话会重复发通知，这里处理重复发通知的问题
            //如果支付状态为已支付直接return;不用带参数


            //处理订单

            //成功然后返回响应
            response.setStatus(200);
            map.put("code","SUCCESS");
            map.put("message","成功");
            //然后返回,将map转化为json
            return gson.toJson(map);
        }
        catch (Exception e) {
            //失败返回
            response.setStatus(500);
            map.put("code","ERROR");
            map.put("message","失败");
           throw new Exception("平台通知响应失败",e);
        }




    }
}
