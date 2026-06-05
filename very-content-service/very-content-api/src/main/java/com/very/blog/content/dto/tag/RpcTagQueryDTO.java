package com.very.blog.content.dto.tag;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RPC标签查询入参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcTagQueryDTO implements Serializable {

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
     * 是否只查询启用标签
     */
    private Boolean enabledOnly;
}
