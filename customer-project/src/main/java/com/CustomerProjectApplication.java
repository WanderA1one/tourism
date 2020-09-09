package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
//fei的远程调用
@EnableFeignClients
//熔断降级
@EnableHystrix
public class CustomerProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerProjectApplication.class);
    }
}
