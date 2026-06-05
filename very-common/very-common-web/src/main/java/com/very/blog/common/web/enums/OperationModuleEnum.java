package com.very.blog.common.web.enums;

import lombok.Getter;

/**
 * 操作模块枚举
 */
@Getter
public enum OperationModuleEnum {

    /**
     * 认证管理
     */
    AUTH("AUTH", "认证管理"),

    /**
     * 用户管理
     */
    USER("USER", "用户管理"),

    /**
     * 角色管理
     */
    ROLE("ROLE", "角色管理"),

    /**
     * 权限管理
     */
    PERMISSION("PERMISSION", "权限管理"),

    /**
     * 文章管理
     */
    ARTICLE("ARTICLE", "文章管理"),

    /**
     * 分类管理
     */
    CATEGORY("CATEGORY", "分类管理"),

    /**
     * 标签管理
     */
    TAG("TAG", "标签管理");

    /**
     * 枚举编码
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String description;

    /**
     * 构造操作模块枚举
     *
     * @param code 枚举编码
     * @param description 枚举描述
     */
    OperationModuleEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
