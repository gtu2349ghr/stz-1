package com.springclound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
//开始注册中心服务器端
@EnableEurekaServer
@SpringBootApplication
public class EurekaClient6001Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClient6001Application.class, args);
    }

}
