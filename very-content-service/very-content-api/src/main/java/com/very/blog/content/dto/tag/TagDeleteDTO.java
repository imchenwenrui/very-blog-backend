package com.very.blog.content.dto.tag;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 标签删除入参
 */
@Data
public class TagDeleteDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 标签ID
     */
    @NotNull(message = "标签ID不能为空")
    private Long id;
}
