package com.example.demo;

import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class test {

 public  void index1() {

         String url = "https://www.zfok.cn/mapi.php";//支付地址
         String pid = "1344";//商户id
         String type = "wxpay";//支付类型
         String outTradeNo = "ohwdq807340234723847234";//商户单号
         String notifyUrl = "http://www.pay.com/notify_url.php";//异步通知
         String returnUrl = "";//跳转地址
         String name = "qwdqw";//商品名
         String money = "1";//价格
         String signType = "MD5";//签名类型
         String key = "oCzHv9M8zV9RFa7TMVamiVyrz18yPtT7";//商户密钥

         //参数存入 map
         Map<String, String> sign = new HashMap<>();
         sign.put("pid", pid);
         sign.put("type", type);
         sign.put("out_trade_no", outTradeNo);
         sign.put("notify_url", notifyUrl);
         sign.put("return_url", returnUrl);
         sign.put("name", name);
         sign.put("money", money);

         //根据key升序排序
         sign = sortByKey(sign);

         String signStr = "";

         //遍历map 转成字符串
         for (Map.Entry<String, String> m : sign.entrySet()) {
                 signStr += m.getKey() + "=" + m.getValue() + "&";
         }

         //去掉最后一个 &
         signStr = signStr.substring(0, signStr.length() - 1);

         //最后拼接上KEY
         signStr += key;

         //转为MD5
         signStr = DigestUtils.md5DigestAsHex(signStr.getBytes());

         sign.put("sign_type", signType);
         sign.put("sign", signStr);
         System.out.println("<form id='paying' action='" + url + "/submit.php' method='post'>");
         for (Map.Entry<String, String> m : sign.entrySet()) {
                 System.out.println("<input type='hidden' name='" + m.getKey() + "' value='" + m.getValue() + "'/>");
         }
         System.out.println("<input type='submit' value='正在跳转'>");
         System.out.println("</form>");
         System.out.println("<script>document.forms['paying'].submit();</script>");
 }



    public static <K extends Comparable<? super K>, V > Map<K, V> sortByKey(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();

        map.entrySet().stream()
                .sorted(Map.Entry.<K, V>comparingByKey()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }


}