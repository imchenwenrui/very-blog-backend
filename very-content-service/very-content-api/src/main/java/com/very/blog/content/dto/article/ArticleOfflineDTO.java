package com.very.blog.content.dto.article;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 文章下线入参
 */
@Data
public class ArticleOfflineDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @NotNull(message = "文章ID不能为空")
    private Long id;
}
