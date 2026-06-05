package com.very.blog.content.dto.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * RPC分类列表查询入参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcCategoryListDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID列表
     */
    @NotEmpty(message = "分类ID列表不能为空")
    private List<Long> ids;

    /**
     * 是否只查询启用分类
     */
    private Boolean enabledOnly;
}
