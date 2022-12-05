package com.bjpowernode.p2p.Timer;
import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.server.loan.IncomeRecoredService;
import com.bjpowernode.srevice.WxpayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时器类
 */
@Async
@Component
@Slf4j
public class Timer {
//   @Reference(interfaceClass = IncomeRecoredService.class,version = "1.0.0",check = false)
//      IncomeRecoredService incomeRecoredService;
//  @Reference(interfaceClass = WxpayService.class,version = "1.0.0",check = false)
//      WxpayService wxpayService;
//    @Scheduled(cron = "0/5 * * * * *")
//    public void generateIncome() throws Exception {
//        System.out.println("======生成收益开始=======");
//           int rows= incomeRecoredService.generateIncomes();
//           if(rows==0){
//               throw new Exception("生成收益失败");
//           }else {
//               System.out.println("生成收益成功");
//           }
//        System.out.println("======生成收益结束=======");
//    }

//    @Scheduled(cron = "0/5 * * * * *")
//    public void generateIncomeBack() throws Exception {
//        log.info("======返还收益开始=======");
//        System.out.println("======返还收益开始=======");
//     int rows=incomeRecoredService.incomeRecoredBack();
//
//            System.out.println("生成收益成功");
//
//        System.out.println("======返还收益结束=======");
//    }
//    @Scheduled(cron = "0/3 * * * * *")
//    public void  cheackOrderNo11(){
//        log.info("查单接口开始了");
//        System.out.println("查单接口开始了");
//        //调用检查订单状态的接口
//        try {
//           wxpayService.cheackOrderin();
//            System.out.println("查单接口成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

@Scheduled(cron = "0/1 * * * * *")
    public void aaaa90ia(){
        System.out.println("============zhixing ");
    }
}
