package com.bjpowernode.srevice;
import com.bjpowernode.Config.WxPayConfig;
import com.bjpowernode.entity.OrderInfo;
import com.bjpowernode.enums.OrderStatus;
import com.bjpowernode.enums.wxpay.WxApiType;
import com.bjpowernode.enums.wxpay.WxNotifyType;
import com.bjpowernode.utils.OrderNoUtils;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
@Slf4j
public class WxpayServiceImpl implements WxpayService {
@Autowired
 private WxPayConfig wxPayConfig;
@Autowired
private CloseableHttpClient PayClient;
    @Override
    @SneakyThrows
    public Map<String, Object> payOrder(Double money) {
//       Double money1=money*100;
        //生成订单号订单号
        String orderNo = OrderNoUtils.getOrderNo();
        //然后创建订单对象，然后将需要的值写入
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(orderNo);
        orderInfo.setTitle("test");
        //这是分
        int money2= (int) (money*100);
        orderInfo.setTotalFee(money2);//分因为微信支付是按照分来计算的
        orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType());//未支付
        //下单
        //这个里面是请求的地址：微信服务器地址+url地址（native接口的地址）
        HttpPost httpPost = new HttpPost(wxPayConfig.getDomain().concat(WxApiType.NATIVE_PAY.getType()));
        // 请求body参数
        //然后new一个map进行参数的封装
        Map paramsMap = new HashMap();
        paramsMap.put("appid",wxPayConfig.getAppid());
        paramsMap.put("mchid",wxPayConfig.getMchId());
        paramsMap.put("description",orderInfo.getTitle());
        paramsMap.put("out_trade_no",orderNo);
        //通知地址，微信发给商家的，我们用外网穿透地址＋上接受地址，响应返回的地址
        paramsMap.put("notify_url", wxPayConfig.getNotifyDomain().concat(WxNotifyType.NATIVE_NOTIFY.getType()));
       //订单金额，因为要传入两个参数，所以用套入map实现
        HashMap<Object, Object> Map = new HashMap<>();
        //总金额
        Map.put("total",orderInfo.getTotalFee());
        //货币类型
        Map.put("currency","CNY");
        paramsMap.put("amount",Map);
        //然后将他们转化为json格式
        Gson gson = new Gson();
        String jsonParams = gson.toJson(paramsMap);
         log.info("请求参数"+jsonParams);

            //将参数封装进entity中
        StringEntity entity = new StringEntity(jsonParams,"utf-8");
        //因为httpclient是json格式接受所以设置一下，用hson做数据交换
        entity.setContentType("application/json");
        //将它放入httpPost中
        httpPost.setEntity(entity);
        //同样设置格式
        httpPost.setHeader("Accept", "application/json");
        //完成签名并执行请求
        //将验签的结果生辰大哥httpclent注入进来
        CloseableHttpResponse response = null;
        try {
            //将请求发送出去，得到反会的结果对象
            response = PayClient.execute(httpPost);

        } catch (IOException e) {
            e.printStackTrace();
        }
//然后对返回的结果进行验证
        try {
            String responBody = EntityUtils.toString(response.getEntity());//响应体
            int statusCode = response.getStatusLine().getStatusCode();//响应状态码
            if (statusCode == 200) { //处理成功
                System.out.println("成功，返回结果 = " + responBody);
            } else if (statusCode == 204) { //处理成功，无返回Body
                System.out.println("success");
            } else {
                System.out.println("native下单失败,响应码 = " + statusCode+ ",返回结果 = " + responBody);
                throw new IOException("request failed");
            }
            //如果成功了就拿到它的结果
            HashMap <String,String>resultMap = gson.fromJson(responBody, HashMap.class);
            //拿到二维码连接
            String code_url = resultMap.get("code_url");
            //然后返回结果
            HashMap<String, Object> map = new HashMap<>();
            map.put("codeUrl",code_url);
            map.put("orderNo",orderInfo.getOrderNo());
            return map;

        } finally {
            response.close();
        }
    }

    /**
     * 主动定时查单每三十秒查看超过5分钟未支付的订单
     * @param orderNo
     * @return
     */
    @Override
    public String querryOrderNo(String orderNo) throws IOException {
        //将orderNo替换占位符
        String url= String.format(WxApiType.ORDER_QUERY_BY_NO.getType(),orderNo);
//拼接字符串
        //服务器地址+查询订单接口+mchid
        url=wxPayConfig.getDomain().concat(url).concat("?mchid=").concat(wxPayConfig.getMchId());
        //封装为httppost
        HttpGet httpGet = new HttpGet(url);
        //设置请求头(希望接受的是json的类型的数据)
        httpGet.setHeader("Accept","application/json");
        CloseableHttpResponse response = PayClient.execute(httpGet);
        try {
            String responBody = EntityUtils.toString(response.getEntity());//响应体
            int statusCode = response.getStatusLine().getStatusCode();//响应状态码
            if (statusCode == 200) { //处理成功
                System.out.println("成功，返回结果 = " + responBody);
            } else if (statusCode == 204) { //处理成功，无返回Body
                System.out.println("success");
            } else {
                System.out.println("查找失败 = " + statusCode+ ",返回结果 = " + responBody);
                throw new IOException("request failed");
            }
            Gson gson = new Gson();
            HashMap <String,String>resultMap = gson.fromJson(responBody, HashMap.class);
            String trade_state = resultMap.get("trade_state");
            return trade_state;


    }finally {
            response.close();
        }


}
}
