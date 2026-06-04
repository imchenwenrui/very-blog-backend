package com.very.blog.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.very.blog.common.core.result.PageResult;
import com.very.blog.user.convert.RoleConvert;
import com.very.blog.user.dto.RoleCreateDTO;
import com.very.blog.user.dto.RolePageDTO;
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
     * 分页查询角色列表
     *
     * @param dto 角色分页查询入参
     * @return 角色分页列表
     */
    @Override
    public PageResult<RoleVO> page(RolePageDTO dto) {
        Page<SysRole> page = sysRoleMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()),
                Wrappers.<SysRole>lambdaQuery()
                        .eq(SysRole::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                        .and(dto.getKeyword() != null && !dto.getKeyword().isBlank(), wrapper -> wrapper
                                .like(SysRole::getRoleCode, dto.getKeyword())
                                .or()
                                .like(SysRole::getRoleName, dto.getKeyword()))
                        .orderByAsc(SysRole::getSortOrder)
                        .orderByDesc(SysRole::getId));
        return PageResult.of(page.getTotal(), page.getRecords().stream()
                .map(RoleConvert::toVO)
                .toList());
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
