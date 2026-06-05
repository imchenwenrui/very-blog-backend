package com.very.blog.user.vo;

import com.very.blog.user.enums.EnableStatusEnum;
import com.very.blog.user.enums.PermissionTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限信息出参
 */
@Data
public class PermissionVO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    private Long id;

    /**
     * 父级权限ID
     */
    private Long parentId;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限类型，取值来源于 {@link PermissionTypeEnum}
     */
    private Integer permissionType;

    /**
     * 前端路由或接口路径
     */
    private String path;

    /**
     * 接口请求方法
     */
    private String httpMethod;

    /**
     * 权限状态，取值来源于 {@link EnableStatusEnum}
     */
    private Integer status;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
