package com.very.blog.user.service;

import com.very.blog.user.dto.LoginDTO;
import com.very.blog.user.vo.LoginVO;
import com.very.blog.user.vo.UserVO;

/**
 * 登录认证服务
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param dto 登录入参
     * @return 登录结果
     */
    LoginVO login(LoginDTO dto);

    /**
     * 用户登出
     */
    void logout();

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户信息
     */
    UserVO currentUser();
}
