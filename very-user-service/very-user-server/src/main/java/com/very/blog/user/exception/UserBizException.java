package com.very.blog.user.exception;

/**
 * 用户服务业务异常
 */
public class UserBizException extends RuntimeException {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 构造用户服务业务异常
     *
     * @param message 异常消息
     */
    public UserBizException(String message) {
        super(message);
    }
}
