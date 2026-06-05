package com.very.blog.content.vo.category;

import com.very.blog.content.enums.common.EnableStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分类树出参
 */
@Data
public class CategoryTreeVO implements Serializable {

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
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 状态，取值来源于 {@link EnableStatusEnum}
     */
    private Integer status;

    /**
     * 子级分类列表
     */
    private List<CategoryTreeVO> children = new ArrayList<>();
}
