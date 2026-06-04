package com.very.blog.common.web.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.very.blog.common.web.annotation.OperationLog;
import com.very.blog.common.web.enums.OperationContentEnum;
import com.very.blog.common.web.enums.OperationStatusEnum;
import com.very.blog.common.web.log.OperationLogContext;
import com.very.blog.common.web.log.OperationLogRecorder;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 接口请求日志切面
 */
@Slf4j
@Aspect
public class ApiLogAspect {

    /**
     * 脱敏占位符
     */
    private static final String MASK_TEXT = "***";

    /**
     * 未知内容
     */
    private static final String UNKNOWN_TEXT = "-";

    /**
     * JSON序列化器
     */
    private final ObjectMapper objectMapper;

    /**
     * 操作日志记录器
     */
    private final ObjectProvider<OperationLogRecorder> operationLogRecorderProvider;

    /**
     * 构造接口请求日志切面
     *
     * @param objectMapper JSON序列化器
     * @param operationLogRecorderProvider 操作日志记录器
     */
    public ApiLogAspect(ObjectMapper objectMapper, ObjectProvider<OperationLogRecorder> operationLogRecorderProvider) {
        this.objectMapper = objectMapper;
        this.operationLogRecorderProvider = operationLogRecorderProvider;
    }

    /**
     * 打印接口请求日志
     *
     * @param joinPoint 切点
     * @return 接口返回值
     * @throws Throwable 接口异常
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object logApiRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        HttpServletRequest request = getRequest();
        String requestBody = getRequestBody(joinPoint.getArgs());
        OperationLog operationLog = getOperationLog(joinPoint);
        OperationLogContext operationLogContext = buildOperationLogContext(operationLog, request, joinPoint, requestBody);
        prepareOperationLog(operationLogContext);
        try {
            Object result = joinPoint.proceed();
            long costTime = getCostTime(startTime);
            log.info("api request completed method={}, url={}, query={}, ip={}, handler={}, requestBody={}, cost={}ms",
                    getMethod(request),
                    getRequestUrl(request),
                    getQueryString(request),
                    getClientIp(request),
                    joinPoint.getSignature().toShortString(),
                    requestBody,
                    costTime);
            recordOperationLog(operationLogContext, OperationStatusEnum.SUCCESS, null, costTime);
            return result;
        } catch (Throwable throwable) {
            long costTime = getCostTime(startTime);
            log.error("api request failed method={}, url={}, query={}, ip={}, handler={}, requestBody={}, cost={}ms, error={}",
                    getMethod(request),
                    getRequestUrl(request),
                    getQueryString(request),
                    getClientIp(request),
                    joinPoint.getSignature().toShortString(),
                    requestBody,
                    costTime,
                    throwable.getMessage(),
                    throwable);
            if (!isLoginOperation(operationLog)) {
                recordOperationLog(operationLogContext, OperationStatusEnum.FAIL, throwable.getMessage(), costTime);
            }
            throw throwable;
        }
    }

    /**
     * 获取操作日志注解
     *
     * @param joinPoint 切点
     * @return 操作日志注解
     */
    private OperationLog getOperationLog(ProceedingJoinPoint joinPoint) {
        if (joinPoint.getSignature() instanceof MethodSignature methodSignature) {
            Method method = methodSignature.getMethod();
            return AnnotationUtils.findAnnotation(method, OperationLog.class);
        }
        return null;
    }

    /**
     * 构建操作日志上下文
     *
     * @param operationLog 操作日志注解
     * @param request 当前请求对象
     * @param joinPoint 切点
     * @param requestBody 请求体
     * @return 操作日志上下文
     */
    private OperationLogContext buildOperationLogContext(OperationLog operationLog,
                                                         HttpServletRequest request,
                                                         ProceedingJoinPoint joinPoint,
                                                         String requestBody) {
        if (operationLog == null) {
            return null;
        }
        OperationLogContext context = new OperationLogContext();
        context.setOperationTime(LocalDateTime.now());
        context.setOperationModule(operationLog.module().getCode());
        context.setOperationModuleName(operationLog.module().getDescription());
        context.setOperationContent(operationLog.content().getCode());
        context.setOperationContentName(operationLog.content().getDescription());
        context.setRequestMethod(getMethod(request));
        context.setRequestUrl(getRequestUrl(request));
        context.setRequestBody(requestBody);
        context.setClientIp(getClientIp(request));
        context.setHandlerMethod(joinPoint.getSignature().toShortString());
        return context;
    }

    /**
     * 预处理操作日志
     *
     * @param context 操作日志上下文
     */
    private void prepareOperationLog(OperationLogContext context) {
        if (context == null) {
            return;
        }
        OperationLogRecorder operationLogRecorder = operationLogRecorderProvider.getIfAvailable();
        if (operationLogRecorder == null) {
            return;
        }
        try {
            operationLogRecorder.prepare(context);
        } catch (Exception e) {
            log.warn("operation log prepare failed, error={}", e.getMessage(), e);
        }
    }

    /**
     * 记录操作日志
     *
     * @param context 操作日志上下文
     * @param status 操作状态
     * @param errorMessage 错误信息
     * @param costTime 接口耗时
     */
    private void recordOperationLog(OperationLogContext context,
                                    OperationStatusEnum status,
                                    String errorMessage,
                                    long costTime) {
        if (context == null) {
            return;
        }
        OperationLogRecorder operationLogRecorder = operationLogRecorderProvider.getIfAvailable();
        if (operationLogRecorder == null) {
            return;
        }
        context.setOperationStatus(status.getCode());
        context.setErrorMessage(errorMessage);
        context.setCostTime(costTime);
        try {
            operationLogRecorder.record(context);
        } catch (Exception e) {
            log.warn("operation log record failed, error={}", e.getMessage(), e);
        }
    }

    /**
     * 判断是否为登录操作
     *
     * @param operationLog 操作日志注解
     * @return 是否为登录操作
     */
    private boolean isLoginOperation(OperationLog operationLog) {
        return operationLog != null && OperationContentEnum.LOGIN.equals(operationLog.content());
    }

    /**
     * 获取当前请求对象
     *
     * @return 当前请求对象
     */
    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    /**
     * 获取请求方法
     *
     * @param request 当前请求对象
     * @return 请求方法
     */
    private String getMethod(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN_TEXT;
        }
        return request.getMethod();
    }

    /**
     * 获取请求地址
     *
     * @param request 当前请求对象
     * @return 请求地址
     */
    private String getRequestUrl(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN_TEXT;
        }
        return request.getRequestURI();
    }

    /**
     * 获取查询参数
     *
     * @param request 当前请求对象
     * @return 查询参数
     */
    private String getQueryString(HttpServletRequest request) {
        if (request == null || request.getQueryString() == null) {
            return UNKNOWN_TEXT;
        }
        return request.getQueryString();
    }

    /**
     * 获取客户端IP
     *
     * @param request 当前请求对象
     * @return 客户端IP
     */
    private String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN_TEXT;
        }
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) {
            return realIp;
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取请求体
     *
     * @param args 接口入参列表
     * @return 请求体JSON
     */
    private String getRequestBody(Object[] args) {
        List<Object> requestArgs = new ArrayList<>();
        for (Object arg : args) {
            if (isLoggableArg(arg)) {
                requestArgs.add(arg);
            }
        }
        if (requestArgs.isEmpty()) {
            return UNKNOWN_TEXT;
        }
        Object body = requestArgs.size() == 1 ? requestArgs.get(0) : requestArgs;
        return toJson(maskSensitiveValue(objectMapper.valueToTree(body)));
    }

    /**
     * 判断是否为可打印入参
     *
     * @param arg 接口入参
     * @return 是否可打印
     */
    private boolean isLoggableArg(Object arg) {
        return !(arg == null
                || arg instanceof ServletRequest
                || arg instanceof ServletResponse
                || arg instanceof BindingResult
                || arg instanceof MultipartFile);
    }

    /**
     * 脱敏敏感字段
     *
     * @param jsonNode JSON节点
     * @return 脱敏后的JSON节点
     */
    private JsonNode maskSensitiveValue(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectNode) {
            Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (isSensitiveField(field.getKey())) {
                    objectNode.put(field.getKey(), MASK_TEXT);
                } else {
                    maskSensitiveValue(field.getValue());
                }
            }
        } else if (jsonNode instanceof ArrayNode arrayNode) {
            for (JsonNode item : arrayNode) {
                maskSensitiveValue(item);
            }
        }
        return jsonNode;
    }

    /**
     * 判断是否为敏感字段
     *
     * @param fieldName 字段名称
     * @return 是否为敏感字段
     */
    private boolean isSensitiveField(String fieldName) {
        String lowerFieldName = fieldName.toLowerCase();
        return lowerFieldName.contains("password") || lowerFieldName.contains("token");
    }

    /**
     * 转换为JSON
     *
     * @param value 待转换对象
     * @return JSON字符串
     */
    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return UNKNOWN_TEXT;
        }
    }

    /**
     * 获取接口耗时
     *
     * @param startTime 开始时间
     * @return 接口耗时
     */
    private long getCostTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }
}
