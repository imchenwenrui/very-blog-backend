package com.very.blog.content.exception.common;

import com.very.blog.common.core.result.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 内容服务异常处理器
 */
@RestControllerAdvice
public class ContentExceptionHandler {

    /**
     * 处理内容服务业务异常
     *
     * @param exception 内容服务业务异常
     * @return 统一返回结果
     */
    @ExceptionHandler(ContentBizException.class)
    public Result<Void> handleContentBizException(ContentBizException exception) {
        return Result.fail(400, exception.getMessage());
    }

    /**
     * 处理请求体参数校验异常
     *
     * @param exception 参数校验异常
     * @return 统一返回结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().isEmpty()
                ? "参数校验失败"
                : exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return Result.fail(400, message);
    }

    /**
     * 处理绑定参数校验异常
     *
     * @param exception 绑定参数校验异常
     * @return 统一返回结果
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException exception) {
        String message = exception.getBindingResult().getFieldErrors().isEmpty()
                ? "参数校验失败"
                : exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return Result.fail(400, message);
    }
}
