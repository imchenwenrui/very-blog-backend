package com.very.blog.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户登录出参
 */
@Data
public class LoginVO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * token名称
     */
    private String tokenName;

    /**
     * token值
     */
    private String tokenValue;

    /**
     * 角色编码列表
     */
    private List<String> roleCodes;

    /**
     * 权限编码列表
     */
    private List<String> permissionCodes;
}
