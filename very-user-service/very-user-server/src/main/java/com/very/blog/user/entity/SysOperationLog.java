package com.very.blog.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.very.blog.common.web.enums.OperationContentEnum;
import com.very.blog.common.web.enums.OperationModuleEnum;
import com.very.blog.common.web.enums.OperationStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 后台操作日志实体
 */
@Data
@TableName("sys_operation_log")
public class SysOperationLog {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 操作模块编码，取值来源于 {@link OperationModuleEnum}
     */
    private String operationModule;

    /**
     * 操作模块名称，取值来源于 {@link OperationModuleEnum}
     */
    private String operationModuleName;

    /**
     * 操作内容编码，取值来源于 {@link OperationContentEnum}
     */
    private String operationContent;

    /**
     * 操作内容名称，取值来源于 {@link OperationContentEnum}
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
     * 操作状态，取值来源于 {@link OperationStatusEnum}
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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
