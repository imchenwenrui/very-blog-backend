package com.very.blog.content.vo.tag;

import com.very.blog.content.enums.common.EnableStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RPC标签信息出参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcTagVO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 标签ID
     */
    private Long id;

    /**
     * 标签编码
     */
    private String tagCode;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签状态，取值来源于 {@link EnableStatusEnum}
     */
    private Integer status;
}
