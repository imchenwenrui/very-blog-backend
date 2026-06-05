package com.very.blog.content.dto.article;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * RPC文章列表查询入参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcArticleListDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID列表
     */
    @NotEmpty(message = "文章ID列表不能为空")
    private List<Long> ids;

    /**
     * 是否只查询已发布文章
     */
    private Boolean publishedOnly;
}
