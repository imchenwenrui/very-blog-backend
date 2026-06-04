package com.very.blog.user.log;

import cn.dev33.satoken.stp.StpUtil;
import com.very.blog.common.web.log.OperationLogContext;
import com.very.blog.common.web.log.OperationLogRecorder;
import com.very.blog.user.entity.SysOperationLog;
import com.very.blog.user.entity.SysUser;
import com.very.blog.user.mapper.SysOperationLogMapper;
import com.very.blog.user.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 用户服务操作日志记录器
 */
@Slf4j
@Component
public class UserOperationLogRecorder implements OperationLogRecorder {

    /**
     * 操作人名称最大长度
     */
    private static final int OPERATOR_NAME_MAX_LENGTH = 64;

    /**
     * 请求地址最大长度
     */
    private static final int REQUEST_URL_MAX_LENGTH = 512;

    /**
     * 处理方法最大长度
     */
    private static final int HANDLER_METHOD_MAX_LENGTH = 256;

    /**
     * 错误信息最大长度
     */
    private static final int ERROR_MESSAGE_MAX_LENGTH = 1024;

    /**
     * 后台操作日志Mapper
     */
    private final SysOperationLogMapper sysOperationLogMapper;

    /**
     * 系统用户Mapper
     */
    private final SysUserMapper sysUserMapper;

    /**
     * 构造用户服务操作日志记录器
     *
     * @param sysOperationLogMapper 后台操作日志Mapper
     * @param sysUserMapper 系统用户Mapper
     */
    public UserOperationLogRecorder(SysOperationLogMapper sysOperationLogMapper, SysUserMapper sysUserMapper) {
        this.sysOperationLogMapper = sysOperationLogMapper;
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 预处理操作日志上下文
     *
     * @param context 操作日志上下文
     */
    @Override
    public void prepare(OperationLogContext context) {
        fillOperator(context);
    }

    /**
     * 记录操作日志
     *
     * @param context 操作日志上下文
     */
    @Override
    public void record(OperationLogContext context) {
        fillOperator(context);
        sysOperationLogMapper.insert(toEntity(context));
    }

    /**
     * 填充操作人信息
     *
     * @param context 操作日志上下文
     */
    private void fillOperator(OperationLogContext context) {
        if (context.getOperatorId() != null) {
            return;
        }
        try {
            if (!StpUtil.isLogin()) {
                return;
            }
            Long userId = StpUtil.getLoginIdAsLong();
            context.setOperatorId(userId);
            context.setOperatorName(getOperatorName(userId));
        } catch (Exception e) {
            log.warn("fill operation log operator failed, error={}", e.getMessage());
        }
    }

    /**
     * 查询操作人名称
     *
     * @param userId 用户ID
     * @return 操作人名称
     */
    private String getOperatorName(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            return String.valueOf(userId);
        }
        if (user.getNickname() != null && !user.getNickname().isBlank()) {
            return truncate(user.getNickname(), OPERATOR_NAME_MAX_LENGTH);
        }
        if (user.getUsername() != null && !user.getUsername().isBlank()) {
            return truncate(user.getUsername(), OPERATOR_NAME_MAX_LENGTH);
        }
        return String.valueOf(userId);
    }

    /**
     * 转换为后台操作日志实体
     *
     * @param context 操作日志上下文
     * @return 后台操作日志实体
     */
    private SysOperationLog toEntity(OperationLogContext context) {
        SysOperationLog entity = new SysOperationLog();
        entity.setOperatorId(context.getOperatorId());
        entity.setOperatorName(truncate(context.getOperatorName(), OPERATOR_NAME_MAX_LENGTH));
        entity.setOperationTime(context.getOperationTime());
        entity.setOperationModule(context.getOperationModule());
        entity.setOperationModuleName(context.getOperationModuleName());
        entity.setOperationContent(context.getOperationContent());
        entity.setOperationContentName(context.getOperationContentName());
        entity.setRequestMethod(context.getRequestMethod());
        entity.setRequestUrl(truncate(context.getRequestUrl(), REQUEST_URL_MAX_LENGTH));
        entity.setRequestBody(context.getRequestBody());
        entity.setClientIp(context.getClientIp());
        entity.setHandlerMethod(truncate(context.getHandlerMethod(), HANDLER_METHOD_MAX_LENGTH));
        entity.setOperationStatus(context.getOperationStatus());
        entity.setErrorMessage(truncate(context.getErrorMessage(), ERROR_MESSAGE_MAX_LENGTH));
        entity.setCostTime(context.getCostTime());
        entity.setCreateTime(LocalDateTime.now());
        return entity;
    }

    /**
     * 截断字符串
     *
     * @param value 原始字符串
     * @param maxLength 最大长度
     * @return 截断后的字符串
     */
    private String truncate(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength);
    }
}
