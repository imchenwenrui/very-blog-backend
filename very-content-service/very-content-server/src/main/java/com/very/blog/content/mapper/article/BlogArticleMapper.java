package com.very.blog.content.mapper.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.very.blog.content.entity.article.BlogArticle;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章Mapper
 */
@Mapper
public interface BlogArticleMapper extends BaseMapper<BlogArticle> {
}
