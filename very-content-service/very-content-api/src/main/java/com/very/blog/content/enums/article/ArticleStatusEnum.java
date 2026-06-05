package com.very.blog.content.enums.article;

import lombok.Getter;

/**
 * 文章状态枚举
 */
@Getter
public enum ArticleStatusEnum {

    /**
     * 草稿
     */
    DRAFT(1, "草稿"),

    /**
     * 已发布
     */
    PUBLISHED(2, "已发布"),

    /**
     * 已下线
     */
    OFFLINE(3, "已下线");

    /**
     * 枚举编码
     */
    private final Integer code;

    /**
     * 枚举描述
     */
    private final String description;

    /**
     * 构造文章状态枚举
     *
     * @param code 枚举编码
     * @param description 枚举描述
     */
    ArticleStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
