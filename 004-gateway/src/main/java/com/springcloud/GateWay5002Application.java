package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@EnableEurekaClient
@SpringBootApplication
public class GateWay5002Application {
//    @Bean
//    KeyResolver userKeyResolver() {    return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));}
    public static void main(String[] args) {
        SpringApplication.run(GateWay5002Application.class, args);
    }

}
