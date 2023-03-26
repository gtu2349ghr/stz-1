package com.springclouduul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
//开启网关代理
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class EurekaZuul5001Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaZuul5001Application.class, args);
    }

}
