package com.very.blog.user.convert;

import com.very.blog.user.dto.PermissionCreateDTO;
import com.very.blog.user.dto.PermissionUpdateDTO;
import com.very.blog.user.entity.SysPermission;
import com.very.blog.user.vo.PermissionTreeVO;
import com.very.blog.user.vo.PermissionVO;

/**
 * 权限对象转换工具
 */
public final class PermissionConvert {

    /**
     * 工具类构造方法
     */
    private PermissionConvert() {
    }

    /**
     * 将权限创建入参转换为权限实体
     *
     * @param dto 权限创建入参
     * @return 权限实体
     */
    public static SysPermission toEntity(PermissionCreateDTO dto) {
        SysPermission entity = new SysPermission();
        entity.setParentId(dto.getParentId());
        entity.setPermissionCode(dto.getPermissionCode());
        entity.setPermissionName(dto.getPermissionName());
        entity.setPermissionType(dto.getPermissionType());
        entity.setPath(dto.getPath());
        entity.setHttpMethod(dto.getHttpMethod());
        entity.setStatus(dto.getStatus());
        entity.setSortOrder(dto.getSortOrder());
        entity.setRemark(dto.getRemark());
        return entity;
    }

    /**
     * 将权限更新入参转换为权限实体
     *
     * @param dto 权限更新入参
     * @return 权限实体
     */
    public static SysPermission toEntity(PermissionUpdateDTO dto) {
        SysPermission entity = new SysPermission();
        entity.setId(dto.getId());
        entity.setParentId(dto.getParentId());
        entity.setPermissionCode(dto.getPermissionCode());
        entity.setPermissionName(dto.getPermissionName());
        entity.setPermissionType(dto.getPermissionType());
        entity.setPath(dto.getPath());
        entity.setHttpMethod(dto.getHttpMethod());
        entity.setStatus(dto.getStatus());
        entity.setSortOrder(dto.getSortOrder());
        entity.setRemark(dto.getRemark());
        return entity;
    }

    /**
     * 将权限实体转换为权限出参
     *
     * @param entity 权限实体
     * @return 权限出参
     */
    public static PermissionVO toVO(SysPermission entity) {
        if (entity == null) {
            return null;
        }
        PermissionVO vo = new PermissionVO();
        vo.setId(entity.getId());
        vo.setParentId(entity.getParentId());
        vo.setPermissionCode(entity.getPermissionCode());
        vo.setPermissionName(entity.getPermissionName());
        vo.setPermissionType(entity.getPermissionType());
        vo.setPath(entity.getPath());
        vo.setHttpMethod(entity.getHttpMethod());
        vo.setStatus(entity.getStatus());
        vo.setSortOrder(entity.getSortOrder());
        vo.setRemark(entity.getRemark());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    /**
     * 将权限实体转换为权限树节点出参
     *
     * @param entity 权限实体
     * @return 权限树节点出参
     */
    public static PermissionTreeVO toTreeVO(SysPermission entity) {
        PermissionTreeVO vo = new PermissionTreeVO();
        vo.setId(entity.getId());
        vo.setParentId(entity.getParentId());
        vo.setPermissionCode(entity.getPermissionCode());
        vo.setPermissionName(entity.getPermissionName());
        vo.setPermissionType(entity.getPermissionType());
        vo.setPath(entity.getPath());
        vo.setHttpMethod(entity.getHttpMethod());
        vo.setStatus(entity.getStatus());
        vo.setSortOrder(entity.getSortOrder());
        return vo;
    }
}
