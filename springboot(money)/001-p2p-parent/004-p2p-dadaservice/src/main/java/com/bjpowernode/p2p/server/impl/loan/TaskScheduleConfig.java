package com.bjpowernode.p2p.server.impl.loan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**使用多线程的时候，往往需要创建Thread类，或者实现Runnable接口，如果要使用到线程池，我们还需要来创建Executors，
 * 在使用spring中，已经给我们做了很好的支持。只要要@EnableAsync就可以使用多线程
 * 通过spring给我们提供的ThreadPoolTaskExecutor就可以使用线程池。*/
//@Configuration 表示该类是一个配置类
@Configuration
@EnableAsync
//所有的定时任务都放在一个线程池中，定时任务启动时使用不同都线程。
public class TaskScheduleConfig {
    private static final int corePoolSize = 10;         // 默认线程数
    private static final int maxPoolSize = 100;       // 最大线程数
    private static final int keepAliveTime = 10;   // 允许线程空闲时间（单位：默认为秒）,十秒后就把线程关闭
    private static final int queueCapacity = 200;   // 缓冲队列数
    private static final String threadNamePrefix = "incom-task"; // 线程池名前缀

    @Bean("threadPoolTaskExecutor1") // bean的名称，默认为首字母小写的方法名
    public ThreadPoolTaskExecutor getDemoThread(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(keepAliveTime);
        executor.setKeepAliveSeconds(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);

        //线程池拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化
        executor.initialize();

        return executor;
    }
}
