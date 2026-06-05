package com.very.blog.content.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 分类删除入参
 */
@Data
public class CategoryDeleteDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    private Long id;
}
