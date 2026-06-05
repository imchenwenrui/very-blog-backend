package com.very.blog.content.mapper.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.very.blog.content.entity.article.BlogArticleTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签关联Mapper
 */
@Mapper
public interface BlogArticleTagMapper extends BaseMapper<BlogArticleTag> {
}
