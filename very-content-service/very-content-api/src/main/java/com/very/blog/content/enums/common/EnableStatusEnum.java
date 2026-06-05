package com.very.blog.content.enums.common;

import lombok.Getter;

/**
 * 启用状态枚举
 */
@Getter
public enum EnableStatusEnum {

    /**
     * 禁用
     */
    DISABLED(0, "禁用"),

    /**
     * 启用
     */
    ENABLED(1, "启用");

    /**
     * 枚举编码
     */
    private final Integer code;

    /**
     * 枚举描述
     */
    private final String description;

    /**
     * 构造启用状态枚举
     *
     * @param code 枚举编码
     * @param description 枚举描述
     */
    EnableStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
