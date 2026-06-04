package com.very.blog.user.service;

import com.very.blog.common.core.result.PageResult;
import com.very.blog.user.dto.UserCreateDTO;
import com.very.blog.user.dto.UserPageDTO;
import com.very.blog.user.dto.UserUpdateDTO;
import com.very.blog.user.vo.UserVO;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param dto 用户创建入参
     * @return 用户信息
     */
    UserVO create(UserCreateDTO dto);

    /**
     * 更新用户
     *
     * @param dto 用户更新入参
     * @return 用户信息
     */
    UserVO update(UserUpdateDTO dto);

    /**
     * 根据用户ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserVO getById(Long id);

    /**
     * 分页查询用户列表
     *
     * @param dto 用户分页查询入参
     * @return 用户分页列表
     */
    PageResult<UserVO> page(UserPageDTO dto);
}
