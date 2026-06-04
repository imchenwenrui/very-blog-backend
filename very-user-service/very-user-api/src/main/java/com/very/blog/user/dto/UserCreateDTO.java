package com.very.blog.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建入参
 */
@Data
public class UserCreateDTO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 登录账号
     */
    @NotBlank(message = "登录账号不能为空")
    private String username;

    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码不能为空")
    private String password;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户类型
     */
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    /**
     * 用户状态
     */
    @NotNull(message = "用户状态不能为空")
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
