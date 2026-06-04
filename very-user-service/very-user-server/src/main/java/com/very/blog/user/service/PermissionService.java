package com.very.blog.user.service;

import com.very.blog.user.dto.PermissionCreateDTO;
import com.very.blog.user.dto.PermissionUpdateDTO;
import com.very.blog.user.vo.PermissionTreeVO;
import com.very.blog.user.vo.PermissionVO;

import java.util.List;

/**
 * 权限服务
 */
public interface PermissionService {

    /**
     * 创建权限
     *
     * @param dto 权限创建入参
     * @return 权限信息
     */
    PermissionVO create(PermissionCreateDTO dto);

    /**
     * 更新权限
     *
     * @param dto 权限更新入参
     * @return 权限信息
     */
    PermissionVO update(PermissionUpdateDTO dto);

    /**
     * 根据权限ID查询权限
     *
     * @param id 权限ID
     * @return 权限信息
     */
    PermissionVO getById(Long id);

    /**
     * 查询权限树
     *
     * @return 权限树列表
     */
    List<PermissionTreeVO> tree();
}
