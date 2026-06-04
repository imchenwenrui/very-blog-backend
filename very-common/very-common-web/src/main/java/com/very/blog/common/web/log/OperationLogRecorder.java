package com.very.blog.common.web.log;

/**
 * 操作日志记录器
 */
public interface OperationLogRecorder {

    /**
     * 预处理操作日志上下文
     *
     * @param context 操作日志上下文
     */
    default void prepare(OperationLogContext context) {
    }

    /**
     * 记录操作日志
     *
     * @param context 操作日志上下文
     */
    void record(OperationLogContext context);
}
