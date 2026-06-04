package com.very.blog.common.web.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.very.blog.common.web.aspect.ApiLogAspect;
import com.very.blog.common.web.log.OperationLogRecorder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;

/**
 * Web公共能力自动配置
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class VeryWebAutoConfiguration {

    /**
     * 创建接口请求日志切面
     *
     * @param objectMapper JSON序列化器
     * @param operationLogRecorderProvider 操作日志记录器
     * @return 接口请求日志切面
     */
    @Bean
    @ConditionalOnClass(ApiLogAspect.class)
    @ConditionalOnMissingBean
    public ApiLogAspect apiLogAspect(ObjectMapper objectMapper,
                                     ObjectProvider<OperationLogRecorder> operationLogRecorderProvider) {
        return new ApiLogAspect(objectMapper, operationLogRecorderProvider);
    }
}
