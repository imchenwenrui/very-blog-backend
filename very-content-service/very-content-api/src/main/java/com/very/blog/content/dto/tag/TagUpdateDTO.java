package com.very.blog.content.dto.tag;

import com.very.blog.content.enums.common.EnableStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 标签更新入参
 */
@Data
public class TagUpdateDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 标签ID
     */
    @NotNull(message = "标签ID不能为空")
    private Long id;

    /**
     * 标签编码
     */
    @NotBlank(message = "标签编码不能为空")
    private String tagCode;

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空")
    private String tagName;

    /**
     * 标签描述
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
