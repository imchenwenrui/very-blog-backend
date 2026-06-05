package com.very.blog.content.entity.category;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.very.blog.content.enums.common.DeletedStatusEnum;
import com.very.blog.content.enums.common.EnableStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章分类实体
 */
@Data
@TableName("blog_category")
public class BlogCategory {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父级分类ID
     */
    private Long parentId;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 状态，取值来源于 {@link EnableStatusEnum}
     */
    private Integer status;

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
