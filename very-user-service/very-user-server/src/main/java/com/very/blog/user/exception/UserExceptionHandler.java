package com.very.blog.user.exception;

import com.very.blog.common.core.result.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 用户服务异常处理器
 */
@RestControllerAdvice
public class UserExceptionHandler {

    /**
     * 处理用户服务业务异常
     *
     * @param exception 用户服务业务异常
     * @return 统一返回结果
     */
    @ExceptionHandler(UserBizException.class)
    public Result<Void> handleUserBizException(UserBizException exception) {
        return Result.fail(400, exception.getMessage());
    }

    /**
     * 处理请求体参数校验异常
     *
     * @param exception 请求体参数校验异常
     * @return 统一返回结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().isEmpty()
                ? "请求参数不正确"
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
                ? "请求参数不正确"
                : exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return Result.fail(400, message);
    }
}
