package com.bjpowernode.p2p.server.impl.loan;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.server.loan.IncomeRecoredService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Task {
    @Reference(interfaceClass = IncomeRecoredService.class,version = "1.0.0",check = false)
   private IncomeRecoredService incomeRecoredService;
        @Scheduled(cron = "0 0/30 * * * *")
        @Async("threadPoolTaskExecutor1")
    public void generateIncomeBack() throws Exception {
        log.info("======返还收益开始=======");
        System.out.println("======返还收益开始=======");
     incomeRecoredService.incomeRecoredBack();

            System.out.println("生成收益成功");
        System.out.println("======返还收益结束=======");
    }
}
