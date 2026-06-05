package com.very.blog.common.web.enums;

import lombok.Getter;

/**
 * 操作内容枚举
 */
@Getter
public enum OperationContentEnum {

    /**
     * 新增
     */
    CREATE("CREATE", "新增"),

    /**
     * 编辑
     */
    UPDATE("UPDATE", "编辑"),

    /**
     * 删除
     */
    DELETE("DELETE", "删除"),

    /**
     * 登录
     */
    LOGIN("LOGIN", "登录"),

    /**
     * 退出登录
     */
    LOGOUT("LOGOUT", "退出登录"),

    /**
     * 发布
     */
    PUBLISH("PUBLISH", "发布"),

    /**
     * 下线
     */
    OFFLINE("OFFLINE", "下线");

    /**
     * 枚举编码
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String description;

    /**
     * 构造操作内容枚举
     *
     * @param code 枚举编码
     * @param description 枚举描述
     */
    OperationContentEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
