package com.very.blog.content.document.article;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章搜索文档
 */
@Data
@Document(indexName = "blog_article")
public class ArticleDocument {

    /**
     * 文章ID
     */
    @Id
    private Long id;

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
     * 文章内容
     */
    private String content;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 标签ID列表
     */
    private List<Long> tagIds;

    /**
     * 标签名称列表
     */
    private List<String> tagNames;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
