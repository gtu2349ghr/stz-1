package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class EurekaClient7002Application {
@Bean
public RestTemplate getRestTemplate(){
    return new RestTemplate();
}
    public static void main(String[] args) {
        SpringApplication.run(EurekaClient7002Application.class, args);
    }

}
