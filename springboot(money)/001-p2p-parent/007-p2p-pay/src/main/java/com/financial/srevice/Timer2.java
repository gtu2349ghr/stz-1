package com.financial.srevice;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.financial.Config.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Timer2 {
    private Logger logger= LoggerFactory.getLogger(Timer2.class);//拿到日志

        @Scheduled(cron = "* 30 * * * *")
    public void  cheackOrderNo11(){
            logger.info("查单接口日志");
        log.info("查单接口开始了");
        System.out.println("查单接口开始了");
        //调用检查订单状态的接口
        try {
            WxpayService wxpayService=  ApplicationContextUtil.getBean(WxpayService.class);
            wxpayService.cheackOrderin();
            System.out.println("查单接口成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
