package com.very.blog.content.dto.article;

import com.very.blog.content.enums.article.ArticleStatusEnum;
import com.very.blog.content.enums.article.ContentFormatEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 文章创建入参
 */
@Data
public class ArticleCreateDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    /**
     * 文章标题
     */
    @NotBlank(message = "文章标题不能为空")
    private String title;

    /**
     * 文章访问标识
     */
    private String slug;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 封面地址
     */
    private String cover;

    /**
     * 文章内容
     */
    @NotBlank(message = "文章内容不能为空")
    private String content;

    /**
     * 内容格式，取值来源于 {@link ContentFormatEnum}
     */
    @NotNull(message = "内容格式不能为空")
    private Integer contentFormat;

    /**
     * 文章状态，取值来源于 {@link ArticleStatusEnum}
     */
    @NotNull(message = "文章状态不能为空")
    private Integer articleStatus;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 标签ID列表
     */
    private List<Long> tagIds;
}
