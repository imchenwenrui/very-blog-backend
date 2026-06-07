package com.very.blog.user.enums;

import lombok.Getter;

/**
 * 用户类型枚举
 */
@Getter
public enum UserTypeEnum {

    /**
     * 管理员
     */
    ADMIN(0, "管理员"),

    /**
     * 普通用户
     */
    NORMAL(1, "普通用户");

    /**
     * 枚举编码
     */
    private final Integer code;

    /**
     * 枚举描述
     */
    private final String description;

    /**
     * 构造用户类型枚举
     *
     * @param code 枚举编码
     * @param description 枚举描述
     */
    UserTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
