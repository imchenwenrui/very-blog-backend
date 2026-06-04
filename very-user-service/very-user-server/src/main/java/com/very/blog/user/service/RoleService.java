package com.very.blog.user.service;

import com.very.blog.user.dto.RoleCreateDTO;
import com.very.blog.user.dto.RoleUpdateDTO;
import com.very.blog.user.vo.RoleVO;

/**
 * 角色服务
 */
public interface RoleService {

    /**
     * 创建角色
     *
     * @param dto 角色创建入参
     * @return 角色信息
     */
    RoleVO create(RoleCreateDTO dto);

    /**
     * 更新角色
     *
     * @param dto 角色更新入参
     * @return 角色信息
     */
    RoleVO update(RoleUpdateDTO dto);

    /**
     * 根据角色ID查询角色
     *
     * @param id 角色ID
     * @return 角色信息
     */
    RoleVO getById(Long id);
}
