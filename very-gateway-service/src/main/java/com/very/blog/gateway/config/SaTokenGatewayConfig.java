package com.very.blog.gateway.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.reactor.context.SaReactorSyncHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.very.blog.common.core.result.Result;
import org.springframework.http.HttpStatusCode;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

/**
 * Sa-Token 网关鉴权配置
 */
@Configuration
@EnableConfigurationProperties(GatewayAuthProperties.class)
public class SaTokenGatewayConfig {

    /**
     * 未登录状态码
     */
    private static final int UNAUTHORIZED_CODE = 401;

    /**
     * 无权限状态码
     */
    private static final int FORBIDDEN_CODE = 403;

    /**
     * 响应内容类型
     */
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    /**
     * 默认鉴权错误消息
     */
    private static final String DEFAULT_AUTH_ERROR_MESSAGE = "认证失败";

    /**
     * 未登录错误消息
     */
    private static final String NOT_LOGIN_MESSAGE = "请先登录";

    /**
     * 注册 Sa-Token 网关过滤器
     *
     * @param authProperties 网关鉴权配置属性
     * @param objectMapper JSON序列化器
     * @return Sa-Token 网关过滤器
     */
    @Bean
    public SaReactorFilter saReactorFilter(GatewayAuthProperties authProperties, ObjectMapper objectMapper) {
        return new SaReactorFilter()
                .addInclude("/**")
                .setAuth(context -> SaRouter.match("/**")
                        .notMatch(authProperties.getWhiteList().toArray(String[]::new))
                        .check(routerContext -> StpUtil.checkLogin()))
                .setError(throwable -> handleAuthError(throwable, objectMapper));
    }

    /**
     * 处理鉴权异常
     *
     * @param throwable 鉴权异常
     * @param objectMapper JSON序列化器
     * @return 统一响应JSON
     */
    private String handleAuthError(Throwable throwable, ObjectMapper objectMapper) {
        int code = getAuthErrorCode(throwable);
        setResponseStatus(code);
        SaHolder.getResponse().setHeader("Content-Type", CONTENT_TYPE);
        return toJson(Result.fail(code, getAuthErrorMessage(throwable)), objectMapper);
    }

    /**
     * 设置响应状态码
     *
     * @param code 状态码
     */
    private void setResponseStatus(int code) {
        ServerWebExchange exchange = SaReactorSyncHolder.getExchange();
        if (exchange != null) {
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(code));
        }
    }

    /**
     * 获取鉴权异常状态码
     *
     * @param throwable 鉴权异常
     * @return 状态码
     */
    private int getAuthErrorCode(Throwable throwable) {
        if (throwable instanceof NotLoginException) {
            return UNAUTHORIZED_CODE;
        }
        return FORBIDDEN_CODE;
    }

    /**
     * 获取鉴权异常消息
     *
     * @param throwable 鉴权异常
     * @return 鉴权异常消息
     */
    private String getAuthErrorMessage(Throwable throwable) {
        if (throwable instanceof NotLoginException) {
            return NOT_LOGIN_MESSAGE;
        }
        if (throwable.getMessage() == null || throwable.getMessage().isBlank()) {
            return DEFAULT_AUTH_ERROR_MESSAGE;
        }
        return throwable.getMessage();
    }

    /**
     * 转换统一响应为JSON
     *
     * @param result 统一响应结果
     * @param objectMapper JSON序列化器
     * @return 统一响应JSON
     */
    private String toJson(Result<Void> result, ObjectMapper objectMapper) {
        try {
            return objectMapper.writer()
                    .with(JsonGenerator.Feature.ESCAPE_NON_ASCII)
                    .writeValueAsString(result);
        } catch (JsonProcessingException e) {
            return "{\"code\":500,\"message\":\"\\u54cd\\u5e94\\u5e8f\\u5217\\u5316\\u5931\\u8d25\",\"data\":null}";
        }
    }
}
