package com.very.blog.user.enums;

import lombok.Getter;

/**
 * 权限类型枚举
 */
@Getter
public enum PermissionTypeEnum {

    /**
     * 菜单
     */
    MENU(1, "菜单"),

    /**
     * 按钮
     */
    BUTTON(2, "按钮"),

    /**
     * 接口
     */
    API(3, "接口");

    /**
     * 枚举编码
     */
    private final Integer code;

    /**
     * 枚举描述
     */
    private final String description;

    /**
     * 构造权限类型枚举
     *
     * @param code 枚举编码
     * @param description 枚举描述
     */
    PermissionTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
