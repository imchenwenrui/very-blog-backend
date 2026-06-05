package com.very.blog.content.dto.article;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RPC文章查询入参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcArticleQueryDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @NotNull(message = "文章ID不能为空")
    private Long id;

    /**
     * 是否只查询已发布文章
     */
    private Boolean publishedOnly;
}
