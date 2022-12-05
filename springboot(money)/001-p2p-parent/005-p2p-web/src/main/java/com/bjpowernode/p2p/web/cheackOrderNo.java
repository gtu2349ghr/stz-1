package com.bjpowernode.p2p.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.server.loan.RechargerecordService;
import com.bjpowernode.springboot.cons.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class cheackOrderNo {
    @Reference(interfaceClass = RechargerecordService.class,version="1.0.0",timeout = 15000)
    RechargerecordService rechargerecordService;

    @RequestMapping("/cheackNo")
    @ResponseBody
    public Result cheackNo1(
           String orderNo){
        int i = rechargerecordService.cheakOrderNo1( orderNo);

        if(i==1){
            return Result.success("支付成功");
        }else {
            return Result.erro("支付失败");
        }
    }
}
