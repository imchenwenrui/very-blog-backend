package com.very.blog.content.vo.tag;

import com.very.blog.content.enums.common.EnableStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 标签信息出参
 */
@Data
public class TagVO implements Serializable {

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
     * 标签描述
     */
    private String description;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 状态，取值来源于 {@link EnableStatusEnum}
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
