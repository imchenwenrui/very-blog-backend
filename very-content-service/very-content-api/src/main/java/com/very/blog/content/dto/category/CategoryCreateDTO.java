package com.very.blog.content.dto.category;

import com.very.blog.content.enums.common.EnableStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 分类创建入参
 */
@Data
public class CategoryCreateDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 父级分类ID
     */
    private Long parentId;

    /**
     * 分类编码
     */
    @NotBlank(message = "分类编码不能为空")
    private String categoryCode;

    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
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
    @NotNull(message = "状态不能为空")
    private Integer status;
}
