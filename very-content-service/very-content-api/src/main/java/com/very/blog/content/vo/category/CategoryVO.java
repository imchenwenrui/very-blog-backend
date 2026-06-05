package com.very.blog.content.vo.category;

import com.very.blog.content.enums.common.EnableStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类信息出参
 */
@Data
public class CategoryVO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
