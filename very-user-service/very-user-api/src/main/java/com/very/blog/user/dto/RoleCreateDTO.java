package com.very.blog.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色创建入参
 */
@Data
public class RoleCreateDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色状态
     */
    @NotNull(message = "角色状态不能为空")
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
