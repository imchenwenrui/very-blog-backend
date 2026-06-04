package com.very.blog.common.web.annotation;

import com.very.blog.common.web.enums.OperationContentEnum;
import com.very.blog.common.web.enums.OperationModuleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    /**
     * 操作模块
     *
     * @return 操作模块
     */
    OperationModuleEnum module();

    /**
     * 操作内容
     *
     * @return 操作内容
     */
    OperationContentEnum content();
}
