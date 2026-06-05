package com.very.blog.content.entity.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章标签关联实体
 */
@Data
@TableName("blog_article_tag")
public class BlogArticleTag {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 标签ID
     */
    private Long tagId;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
