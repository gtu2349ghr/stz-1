package com.springclound;

import com.springclound.doMain.R;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//微服务注册中心
@EnableEurekaClient
@SpringBootApplication
public class EurekaClient7001Application {
    @Bean
     public RestTemplate getRestTemplate(){
        return  new RestTemplate();

    }


    public static void main(String[] args) {
        SpringApplication.run(EurekaClient7001Application.class, args);
    }

}
