package com.very.blog.user.convert;

import com.very.blog.user.dto.RoleCreateDTO;
import com.very.blog.user.dto.RoleUpdateDTO;
import com.very.blog.user.entity.SysRole;
import com.very.blog.user.vo.RoleVO;

/**
 * 角色对象转换工具
 */
public final class RoleConvert {

    /**
     * 工具类构造方法
     */
    private RoleConvert() {
    }

    /**
     * 将角色创建入参转换为角色实体
     *
     * @param dto 角色创建入参
     * @return 角色实体
     */
    public static SysRole toEntity(RoleCreateDTO dto) {
        SysRole entity = new SysRole();
        entity.setRoleCode(dto.getRoleCode());
        entity.setRoleName(dto.getRoleName());
        entity.setStatus(dto.getStatus());
        entity.setSortOrder(dto.getSortOrder());
        entity.setRemark(dto.getRemark());
        return entity;
    }

    /**
     * 将角色更新入参转换为角色实体
     *
     * @param dto 角色更新入参
     * @return 角色实体
     */
    public static SysRole toEntity(RoleUpdateDTO dto) {
        SysRole entity = new SysRole();
        entity.setId(dto.getId());
        entity.setRoleCode(dto.getRoleCode());
        entity.setRoleName(dto.getRoleName());
        entity.setStatus(dto.getStatus());
        entity.setSortOrder(dto.getSortOrder());
        entity.setRemark(dto.getRemark());
        return entity;
    }

    /**
     * 将角色实体转换为角色出参
     *
     * @param entity 角色实体
     * @return 角色出参
     */
    public static RoleVO toVO(SysRole entity) {
        if (entity == null) {
            return null;
        }
        RoleVO vo = new RoleVO();
        vo.setId(entity.getId());
        vo.setRoleCode(entity.getRoleCode());
        vo.setRoleName(entity.getRoleName());
        vo.setStatus(entity.getStatus());
        vo.setSortOrder(entity.getSortOrder());
        vo.setRemark(entity.getRemark());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }
}
