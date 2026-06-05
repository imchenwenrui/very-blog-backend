package com.very.blog.content.vo.category;

import com.very.blog.content.enums.common.EnableStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RPC分类信息出参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcCategoryVO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    private Long id;

    /**
     * 父级分类ID
     */
    private Long parentId;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类状态，取值来源于 {@link EnableStatusEnum}
     */
    private Integer status;
}
