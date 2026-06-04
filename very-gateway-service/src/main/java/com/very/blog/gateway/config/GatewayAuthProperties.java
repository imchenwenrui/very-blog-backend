package com.very.blog.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 网关鉴权配置属性
 */
@Data
@ConfigurationProperties(prefix = "very.gateway.auth")
public class GatewayAuthProperties {

    /**
     * 放行路径列表
     */
    private List<String> whiteList = List.of(
            "/admin/auth/login",
            "/blog/**",
            "/favicon.ico"
    );
}
