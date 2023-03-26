package com.pay.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class P2pPayDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(P2pPayDemoApplication.class, args);
    }

}
