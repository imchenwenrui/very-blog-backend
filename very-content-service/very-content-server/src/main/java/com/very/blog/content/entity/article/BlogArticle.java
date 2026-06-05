package com.very.blog.content.entity.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.very.blog.content.enums.article.ArticleStatusEnum;
import com.very.blog.content.enums.article.ContentFormatEnum;
import com.very.blog.content.enums.common.DeletedStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章实体
 */
@Data
@TableName("blog_article")
public class BlogArticle {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类ID
     */
    private Long categoryId;

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
     * 内容格式，取值来源于 {@link ContentFormatEnum}
     */
    private Integer contentFormat;

    /**
     * 文章状态，取值来源于 {@link ArticleStatusEnum}
     */
    private Integer articleStatus;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人ID
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标识，取值来源于 {@link DeletedStatusEnum}
     */
    private Integer deleted;
}
