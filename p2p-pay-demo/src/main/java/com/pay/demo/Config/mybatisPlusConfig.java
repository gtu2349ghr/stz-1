package com.pay.demo.Config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@MapperScan("com.pay.demo.mapper")
@Configuration
@EnableTransactionManagement//事务管理
public class mybatisPlusConfig {
}
