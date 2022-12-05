package com.bjpowernode.p2p.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.model.RechargeRecord;
import com.bjpowernode.p2p.model.User;
import com.bjpowernode.p2p.server.loan.KuaiqianService;
import com.bjpowernode.p2p.server.loan.RechargerecordService;
import com.bjpowernode.springboot.cons.Constans;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
public class TochargeController {
     @Reference(interfaceClass = KuaiqianService.class,check = false,version = "1.0.0",timeout = 15000)
     private KuaiqianService kuaiqianService;
    @Reference(interfaceClass = RechargerecordService.class,check = false,version = "1.0.0",timeout = 15000)
    private RechargerecordService rechargerecordService;
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    /**
     *进入充值页面
     */
    @RequestMapping("/loan/page/toRecharge")
    public  String toRecharge(){

        return "toRecharge";
    }

    /**
     * 点击充值后封装参数返回块钱，只要是提交支付表单
     * @return
     */
    @RequestMapping("loan/toRecharge")
    public  String submittoRecharge(HttpServletRequest request,
                                    @RequestParam(value = "rechargeMoney") String Money,
                                    Model model
    ){
        //拿到钱，得到手机号和用户姓名，然后生成订单，封装参数
        User user = (User) request.getSession().getAttribute(Constans.SESSION_USER);
        RechargeRecord rechargeRecord = new RechargeRecord();
        //调用获取订单编号的方法
        String orderid = generateOrderId();
        //用户标识
        rechargeRecord.setUid(user.getId());
        //订单编号
        rechargeRecord.setRechargeNo(orderid);
        //充值金额（以分为单位）
        rechargeRecord.setRechargeMoney(Double.valueOf((int)(Double.parseDouble(Money) * 100)));
        //充值状态（0表示充值中）
        rechargeRecord.setRechargeStatus("0");
        //充值描述
        rechargeRecord.setRechargeDesc("快钱充值");
        //充值时间
        rechargeRecord.setRechargeTime(new Date());
        //充值之前先生成充值记录
        //进行数据封装
       int rows= rechargerecordService.rechagrgeRows(rechargeRecord);
       if(rows==0){
           model.addAttribute("target_msg","新增充值记录失败");
           return "toRechargeBack";
       }
        //然后封装
      Map<String,String> map= kuaiqianService.makeKuaiqianfrom(Money,user.getName(),user.getPhone(), orderid);
       model.addAllAttributes(map);

        return "KuaiQianForm";
    }
    /**
     * 生成订单号
     */
    private String generateOrderId(){
String orderId= "KQ"+ DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
        System.out.println(orderId);
        return orderId;

    }
}
