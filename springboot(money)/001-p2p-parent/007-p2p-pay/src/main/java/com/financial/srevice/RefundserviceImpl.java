package com.financial.srevice;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.financial.Config.ApplicationContextUtil;
import com.financial.Config.WxPayConfig;
import com.financial.p2p.mapper.loan.RefundMapper;
import com.financial.p2p.model.Refund;
import com.financial.p2p.server.user.RefundService;
import com.financial.utils.OrderNoUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Service
@Slf4j
public class RefundserviceImpl implements Refundservice {
    @Reference(interfaceClass = RefundMapper.class,version = "1.0.0",timeout = 15000)
    private RefundMapper refundMapper;
    @Autowired
    private  WxPayConfig wxPayConfig;
    @Autowired
    private CloseableHttpClient payCient;
    @Override
    public void refundApply(String orderNo) {
        RefundService refundService= ApplicationContextUtil.getBean(RefundService.class);

        Refund refund = refundService.selectByOrderNo(orderNo);
        System.out.println(refund);
        int total = refund.getTotal();
        log.info("需要的金额"+total);
        refund.setRefund(total);//设置退款金额，默认全部退款
        //设置退款单号
        String refundNo = OrderNoUtils.getRefundNo();
        log.info("退款编号"+refundNo);
        refund.setOutRefundNo(refundNo);
        //然后封装参数，发送请求
        HashMap<String, Object> map = new HashMap<>();
        //封装返回统治的接口
        map.put("notify_url",wxPayConfig.getNotifyDomain().concat("api/wx-refund/refund"));
        map.put("out_trade_no",refund.getOutTradeNo());
        map.put("out_refund_no",refund.getOutRefundNo());
        HashMap<String, Object> amount = new HashMap<>();
        amount.put("refund",refund.getRefund());
        amount.put("total",refund.getTotal());
        amount.put("currency",refund.getCurrency());
        map.put("amount",amount);
        //然后转化为json
        Gson gson = new Gson();
        //设置url然后生成http请求
        String url= wxPayConfig.getDomain().concat("/v3/refund/domestic/refunds");
        HttpPost httpPost = new HttpPost(url);
        String questMap = gson.toJson(map);
        //设置报文的编码
        StringEntity stringEntity = new StringEntity(questMap, "UTF-8");
        //设置报文请求格式
        stringEntity.setContentType("application/json");
        //设置请求头
        httpPost.setHeader("Accept","application/json");
        //设置内容
        httpPost.setEntity(stringEntity);
        //然后发送
        try {
            CloseableHttpResponse responese = payCient.execute(httpPost);
            //然后刨析结果
            String resoponbody = EntityUtils.toString(responese.getEntity());
            //拿到的返回的参数集合
            if(responese.getStatusLine().getStatusCode()==200){
                log.info("返回成功+结果是+"+resoponbody);
            }else  if(responese.getStatusLine().getStatusCode()==204){
                log.info("成功");
            }
            else {
              throw new Exception("返回异常,状态码为"+responese.getStatusLine().getStatusCode()+"退款返回结果"+resoponbody);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


    }
}
