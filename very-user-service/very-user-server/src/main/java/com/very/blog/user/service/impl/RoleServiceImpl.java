package com.very.blog.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.very.blog.user.convert.RoleConvert;
import com.very.blog.user.dto.RoleCreateDTO;
import com.very.blog.user.dto.RoleUpdateDTO;
import com.very.blog.user.entity.SysRole;
import com.very.blog.user.enums.DeletedStatusEnum;
import com.very.blog.user.exception.UserBizException;
import com.very.blog.user.mapper.SysRoleMapper;
import com.very.blog.user.service.RoleService;
import com.very.blog.user.vo.RoleVO;
import org.springframework.stereotype.Service;

/**
 * 角色服务实现
 */
@Service
public class RoleServiceImpl implements RoleService {

    /**
     * 系统角色Mapper
     */
    private final SysRoleMapper sysRoleMapper;

    /**
     * 构造角色服务实现
     *
     * @param sysRoleMapper 系统角色Mapper
     */
    public RoleServiceImpl(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    /**
     * 创建角色
     *
     * @param dto 角色创建入参
     * @return 角色信息
     */
    @Override
    public RoleVO create(RoleCreateDTO dto) {
        long count = sysRoleMapper.selectCount(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getRoleCode, dto.getRoleCode())
                .eq(SysRole::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (count > 0) {
            throw new UserBizException("角色编码已存在");
        }

        SysRole entity = RoleConvert.toEntity(dto);
        entity.setDeleted(DeletedStatusEnum.NOT_DELETED.getCode());
        sysRoleMapper.insert(entity);
        return RoleConvert.toVO(entity);
    }

    /**
     * 更新角色
     *
     * @param dto 角色更新入参
     * @return 角色信息
     */
    @Override
    public RoleVO update(RoleUpdateDTO dto) {
        getEnabledEntity(dto.getId());
        SysRole entity = RoleConvert.toEntity(dto);
        sysRoleMapper.updateById(entity);
        return getById(dto.getId());
    }

    /**
     * 根据角色ID查询角色
     *
     * @param id 角色ID
     * @return 角色信息
     */
    @Override
    public RoleVO getById(Long id) {
        return RoleConvert.toVO(getEnabledEntity(id));
    }

    /**
     * 查询未删除角色实体
     *
     * @param id 角色ID
     * @return 角色实体
     */
    private SysRole getEnabledEntity(Long id) {
        SysRole role = sysRoleMapper.selectOne(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getId, id)
                .eq(SysRole::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (role == null) {
            throw new UserBizException("角色不存在");
        }
        return role;
    }
}
