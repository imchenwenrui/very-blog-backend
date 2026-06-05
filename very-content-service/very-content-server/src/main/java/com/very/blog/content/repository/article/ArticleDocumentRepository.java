package com.very.blog.content.repository.article;

import com.very.blog.content.document.article.ArticleDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 文章搜索文档仓储
 */
public interface ArticleDocumentRepository extends ElasticsearchRepository<ArticleDocument, Long> {
}
