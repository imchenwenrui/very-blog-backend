package com.very.blog.content.dto.article;

import com.very.blog.content.enums.article.ArticleStatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 文章分页查询入参
 */
@Data
public class ArticlePageDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码不能小于1")
    private Long pageNum;

    /**
     * 每页数量
     */
    @NotNull(message = "每页数量不能为空")
    @Min(value = 1, message = "每页数量不能小于1")
    private Long pageSize;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 文章状态，取值来源于 {@link ArticleStatusEnum}
     */
    private Integer articleStatus;
}
