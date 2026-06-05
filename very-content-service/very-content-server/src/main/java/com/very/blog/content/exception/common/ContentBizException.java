package com.very.blog.content.exception.common;

/**
 * 内容服务业务异常
 */
public class ContentBizException extends RuntimeException {

    /**
     * 构造内容服务业务异常
     *
     * @param message 异常消息
     */
    public ContentBizException(String message) {
        super(message);
    }
}
