package com.very.blog.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录入参
 */
@Data
public class LoginDTO implements Serializable {

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
}
