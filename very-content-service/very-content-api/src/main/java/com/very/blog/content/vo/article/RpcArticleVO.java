package com.very.blog.content.vo.article;

import com.very.blog.content.enums.article.ArticleStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * RPC文章信息出参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcArticleVO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    private Long id;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 文章标题
     */
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
     * 文章状态，取值来源于 {@link ArticleStatusEnum}
     */
    private Integer articleStatus;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 标签ID列表
     */
    private List<Long> tagIds;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
