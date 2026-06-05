package com.very.blog.content.dto.tag;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * RPC标签列表查询入参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcTagListDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 标签ID列表
     */
    @NotEmpty(message = "标签ID列表不能为空")
    private List<Long> ids;

    /**
     * 是否只查询启用标签
     */
    private Boolean enabledOnly;
}
