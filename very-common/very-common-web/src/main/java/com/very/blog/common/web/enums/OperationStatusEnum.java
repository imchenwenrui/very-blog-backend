package com.very.blog.common.web.enums;

import lombok.Getter;

/**
 * 操作状态枚举
 */
@Getter
public enum OperationStatusEnum {

    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    /**
     * 失败
     */
    FAIL(1, "失败");

    /**
     * 枚举编码
     */
    private final Integer code;

    /**
     * 枚举描述
     */
    private final String description;

    /**
     * 构造操作状态枚举
     *
     * @param code 枚举编码
     * @param description 枚举描述
     */
    OperationStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
