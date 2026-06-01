package com.very.blog.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class VeryGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(VeryGatewayApplication.class, args);
    }
}
