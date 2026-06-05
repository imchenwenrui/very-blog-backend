package com.very.blog.user.dto;

import com.very.blog.user.enums.EnableStatusEnum;
import com.very.blog.user.enums.PermissionTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限创建入参
 */
@Data
public class PermissionCreateDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 父级权限ID
     */
    private Long parentId;

    /**
     * 权限编码
     */
    @NotBlank(message = "权限编码不能为空")
    private String permissionCode;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    private String permissionName;

    /**
     * 权限类型，取值来源于 {@link PermissionTypeEnum}
     */
    @NotNull(message = "权限类型不能为空")
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
    @NotNull(message = "权限状态不能为空")
    private Integer status;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 备注
     */
    private String remark;
}
