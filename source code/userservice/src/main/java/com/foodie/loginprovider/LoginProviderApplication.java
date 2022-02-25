package com.foodie.loginprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

//@MapperScan("com.foodie.loginprovider.mapper")
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class LoginProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginProviderApplication.class, args);
    }
}
