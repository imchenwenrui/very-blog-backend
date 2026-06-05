package com.very.blog.content.mapper.category;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.very.blog.content.entity.category.BlogCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章分类Mapper
 */
@Mapper
public interface BlogCategoryMapper extends BaseMapper<BlogCategory> {
}
