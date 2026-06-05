package com.very.blog.content.service.article.impl;

import com.very.blog.content.document.article.ArticleDocument;
import com.very.blog.content.entity.article.BlogArticle;
import com.very.blog.content.entity.category.BlogCategory;
import com.very.blog.content.entity.tag.BlogTag;
import com.very.blog.content.repository.article.ArticleDocumentRepository;
import com.very.blog.content.service.article.ArticleSearchService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章搜索服务实现
 */
@Service
public class ArticleSearchServiceImpl implements ArticleSearchService {

    /**
     * 文章搜索文档仓储
     */
    private final ArticleDocumentRepository articleDocumentRepository;

    /**
     * 构造文章搜索服务实现
     *
     * @param articleDocumentRepository 文章搜索文档仓储
     */
    public ArticleSearchServiceImpl(ArticleDocumentRepository articleDocumentRepository) {
        this.articleDocumentRepository = articleDocumentRepository;
    }

    /**
     * 保存已发布文章搜索文档
     *
     * @param article 文章实体
     * @param category 分类实体
     * @param tags 标签列表
     */
    @Override
    public void savePublishedArticle(BlogArticle article, BlogCategory category, List<BlogTag> tags) {
        ArticleDocument document = new ArticleDocument();
        document.setId(article.getId());
        document.setTitle(article.getTitle());
        document.setSlug(article.getSlug());
        document.setSummary(article.getSummary());
        document.setCover(article.getCover());
        document.setContent(article.getContent());
        document.setCategoryId(article.getCategoryId());
        document.setCategoryName(category == null ? null : category.getCategoryName());
        document.setTagIds(tags.stream().map(BlogTag::getId).toList());
        document.setTagNames(tags.stream().map(BlogTag::getTagName).toList());
        document.setPublishTime(article.getPublishTime());
        document.setUpdateTime(article.getUpdateTime());
        articleDocumentRepository.save(document);
    }

    /**
     * 删除文章搜索文档
     *
     * @param articleId 文章ID
     */
    @Override
    public void deleteArticle(Long articleId) {
        articleDocumentRepository.deleteById(articleId);
    }
}
