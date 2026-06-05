package com.very.blog.content.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RPC分类查询入参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcCategoryQueryDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    private Long id;

    /**
     * 是否只查询启用分类
     */
    private Boolean enabledOnly;
}
