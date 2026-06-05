package com.very.blog.content.mapper.tag;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.very.blog.content.entity.tag.BlogTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签Mapper
 */
@Mapper
public interface BlogTagMapper extends BaseMapper<BlogTag> {
}
