package com.very.blog.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户服务启动类
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class VeryUserApplication {

    /**
     * 启动用户服务
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(VeryUserApplication.class, args);
    }
}
