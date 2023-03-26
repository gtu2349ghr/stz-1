package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableHystrix
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class EurekaClient8002Aoolication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClient8002Aoolication.class, args);
    }

}
