package com.very.blog.content.enums.common;

import lombok.Getter;

/**
 * 删除状态枚举
 */
@Getter
public enum DeletedStatusEnum {

    /**
     * 未删除
     */
    NOT_DELETED(0, "未删除"),

    /**
     * 已删除
     */
    DELETED(1, "已删除");

    /**
     * 枚举编码
     */
    private final Integer code;

    /**
     * 枚举描述
     */
    private final String description;

    /**
     * 构造删除状态枚举
     *
     * @param code 枚举编码
     * @param description 枚举描述
     */
    DeletedStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
