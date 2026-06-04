package com.very.blog.common.web.log;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志上下文
 */
@Data
public class OperationLogContext {

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人名称
     */
    private String operatorName;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * 操作模块编码
     */
    private String operationModule;

    /**
     * 操作模块名称
     */
    private String operationModuleName;

    /**
     * 操作内容编码
     */
    private String operationContent;

    /**
     * 操作内容名称
     */
    private String operationContentName;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestBody;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 处理方法
     */
    private String handlerMethod;

    /**
     * 操作状态
     */
    private Integer operationStatus;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 接口耗时
     */
    private Long costTime;
}
