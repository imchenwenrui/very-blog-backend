package com.very.blog.content.enums.article;

import lombok.Getter;

/**
 * 内容格式枚举
 */
@Getter
public enum ContentFormatEnum {

    /**
     * Markdown
     */
    MARKDOWN(0, "Markdown"),

    /**
     * HTML
     */
    HTML(1, "HTML");

    /**
     * 枚举编码
     */
    private final Integer code;

    /**
     * 枚举描述
     */
    private final String description;

    /**
     * 构造内容格式枚举
     *
     * @param code 枚举编码
     * @param description 枚举描述
     */
    ContentFormatEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
