package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulProjectApplication.class);
    }

}
