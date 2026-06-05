package com.very.blog.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 内容服务启动类
 */
@MapperScan({
        "com.very.blog.content.mapper.article",
        "com.very.blog.content.mapper.category",
        "com.very.blog.content.mapper.tag"
})
@SpringBootApplication
public class VeryContentApplication {

    /**
     * 启动内容服务
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(VeryContentApplication.class, args);
    }
}
