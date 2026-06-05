package com.very.blog.content.service.article;

import com.very.blog.content.entity.article.BlogArticle;
import com.very.blog.content.entity.category.BlogCategory;
import com.very.blog.content.entity.tag.BlogTag;

import java.util.List;

/**
 * 文章搜索服务
 */
public interface ArticleSearchService {

    /**
     * 保存已发布文章搜索文档
     *
     * @param article 文章实体
     * @param category 分类实体
     * @param tags 标签列表
     */
    void savePublishedArticle(BlogArticle article, BlogCategory category, List<BlogTag> tags);

    /**
     * 删除文章搜索文档
     *
     * @param articleId 文章ID
     */
    void deleteArticle(Long articleId);
}
