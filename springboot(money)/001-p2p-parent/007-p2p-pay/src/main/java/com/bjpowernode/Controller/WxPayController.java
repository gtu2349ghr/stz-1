package com.bjpowernode.Controller;

import com.bjpowernode.Config.ApplicationContextUtil;
import com.bjpowernode.Config.WxPayConfig;
import com.bjpowernode.Vo.Result;
import com.bjpowernode.p2p.model.Refund;
import com.bjpowernode.p2p.model.User;
import com.bjpowernode.p2p.server.user.RefundService;
import com.bjpowernode.springboot.cons.Constans;
import com.bjpowernode.srevice.WxpayService;
import com.bjpowernode.utils.HttpUtils;
import com.google.gson.Gson;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("api/wx-pay")
public class WxPayController {
   @Resource
    RedisTemplate<String,Object> redisTemplate;
    //这里注入验签器。获得平台证书
@Resource
  private WxpayService vxpayService;
    @Autowired
    private Verifier PayVerfire;
    @Autowired
    WxPayConfig wxPayConfig;

    @CrossOrigin
    @RequestMapping("test")
    public  Result test(HttpServletResponse response){

        System.out.println("qqqqq");
        return Result.ok();
    }
    @RequestMapping("native")
    public Result Vxpay(@RequestParam(value = "money") Double money
                        ){
        //这里调用service
        //下单功能，返回二维码连接和订单号
        System.out.println(money);
        //获取用户的id
        User sessionUser =(User) redisTemplate.opsForValue().get(Constans.SESSION_USER);
        System.out.println(sessionUser.getId());
        log.info(money+"这是money"+sessionUser+"这是user");
        Map<String,Object> map= vxpayService.payOrder(money, sessionUser);
        log.info("发起支付");
        log.info((String) map.get("orderNo"));
//        HashMap<String, Object> map1 = new HashMap<>();
//        map1.put("codeUrl",map.get("codeUrl"));
//        System.out.println(map.get("codeUrl"));
//        return Result.ok().setData(map);
        return com.bjpowernode.Vo.Result.ok().setData(map);
//        return Result.ok();
    }

    /**
     * 平台发送支付结果
     * @param request
     * @param response
     * @return
     */
    @SneakyThrows
    @RequestMapping("native/notify")
    public  String novxpay(HttpServletRequest request, HttpServletResponse response){
        HashMap<String, Object> map = new HashMap<>();
        try {
            //应答的结果map集合
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
            Object trade_state = hashMap.get("trade_state");
            log.info("平台返回通知+"+"交易状态为"+trade_state);
//            log.info("这是总金额"+);
            //成功然后返回响
            if(trade_state.equals("SUCCESS")){
                String out_trade_no = (String) hashMap.get("out_trade_no");
                log.info(out_trade_no);
                //处理订单
                WxpayService wxpayService= ApplicationContextUtil.getBean(WxpayService.class);
                wxpayService.dowithOrder(out_trade_no);
                //填写退款单的信息
                String transaction_id =(String) hashMap.get("transaction_id");//微信支付订单号
                Refund refund = new Refund();
                refund.setTransactionId(transaction_id);//微信支付订单号
                refund.setOutTradeNo(out_trade_no);//商户订单号
                User user =(User) redisTemplate.opsForValue().get(Constans.SESSION_USER);
                log.info("这是userid"+user.getId());
                refund.setUid(user.getId());
//                String currency =(String) hashMap.get("payer_currency");
//                log.info("这是货币类型"+currency);
                refund.setCurrency("CNY");//货币类型
                Map amount = (Map) hashMap.get("amount");
                log.info(amount+"");
                int total = (int) amount.get("total");
                String currency =(String) amount.get("currency");
                refund.setCurrency(currency);
                refund.setTotal(total);//总金额
                //然后添加数据
                RefundService refundService= ApplicationContextUtil.getBean(RefundService.class);
               int rows= refundService.insertFound(refund);
                response.setStatus(200);
                map.put("code","SUCCESS");
                map.put("message","成功");
                //然后返回,将map转化为json
                return gson.toJson(map);
            }else {
                //失败返回
                response.setStatus(500);
                map.put("code","ERROR");
                map.put("message","失败");
                //然后返回,将map转化为json
                return gson.toJson(map);
            }

        }
        catch (Exception e) {
           throw new Exception("平台通知响应失败",e);
        }
    }
    @RequestMapping("")
    public String querryOrderNo(String orderNo) throws IOException {
        log.info("开始查单,这是查询订单接口");
        WxpayService wxpayService= ApplicationContextUtil.getBean(WxpayService.class);

        String responboy= wxpayService.querryOrderNo(orderNo);
       return responboy;
    }
}
