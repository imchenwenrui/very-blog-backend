package com.very.blog.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.very.blog.user.convert.PermissionConvert;
import com.very.blog.user.dto.PermissionCreateDTO;
import com.very.blog.user.dto.PermissionUpdateDTO;
import com.very.blog.user.entity.SysPermission;
import com.very.blog.user.enums.DeletedStatusEnum;
import com.very.blog.user.exception.UserBizException;
import com.very.blog.user.mapper.SysPermissionMapper;
import com.very.blog.user.service.PermissionService;
import com.very.blog.user.vo.PermissionTreeVO;
import com.very.blog.user.vo.PermissionVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限服务实现
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    /**
     * 系统权限Mapper
     */
    private final SysPermissionMapper sysPermissionMapper;

    /**
     * 构造权限服务实现
     *
     * @param sysPermissionMapper 系统权限Mapper
     */
    public PermissionServiceImpl(SysPermissionMapper sysPermissionMapper) {
        this.sysPermissionMapper = sysPermissionMapper;
    }

    /**
     * 创建权限
     *
     * @param dto 权限创建入参
     * @return 权限信息
     */
    @Override
    public PermissionVO create(PermissionCreateDTO dto) {
        long count = sysPermissionMapper.selectCount(Wrappers.<SysPermission>lambdaQuery()
                .eq(SysPermission::getPermissionCode, dto.getPermissionCode())
                .eq(SysPermission::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (count > 0) {
            throw new UserBizException("权限编码已存在");
        }

        SysPermission entity = PermissionConvert.toEntity(dto);
        if (entity.getParentId() == null) {
            entity.setParentId(0L);
        }
        entity.setDeleted(DeletedStatusEnum.NOT_DELETED.getCode());
        sysPermissionMapper.insert(entity);
        return PermissionConvert.toVO(entity);
    }

    /**
     * 更新权限
     *
     * @param dto 权限更新入参
     * @return 权限信息
     */
    @Override
    public PermissionVO update(PermissionUpdateDTO dto) {
        getEnabledEntity(dto.getId());
        SysPermission entity = PermissionConvert.toEntity(dto);
        if (entity.getParentId() == null) {
            entity.setParentId(0L);
        }
        sysPermissionMapper.updateById(entity);
        return getById(dto.getId());
    }

    /**
     * 根据权限ID查询权限
     *
     * @param id 权限ID
     * @return 权限信息
     */
    @Override
    public PermissionVO getById(Long id) {
        return PermissionConvert.toVO(getEnabledEntity(id));
    }

    /**
     * 查询权限树
     *
     * @return 权限树列表
     */
    @Override
    public List<PermissionTreeVO> tree() {
        List<SysPermission> permissions = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                .eq(SysPermission::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                .orderByAsc(SysPermission::getSortOrder)
                .orderByAsc(SysPermission::getId));

        Map<Long, PermissionTreeVO> nodeMap = new LinkedHashMap<>();
        for (SysPermission permission : permissions) {
            nodeMap.put(permission.getId(), PermissionConvert.toTreeVO(permission));
        }

        List<PermissionTreeVO> roots = new ArrayList<>();
        for (PermissionTreeVO node : nodeMap.values()) {
            Long parentId = node.getParentId();
            PermissionTreeVO parent = parentId == null ? null : nodeMap.get(parentId);
            if (parent == null || parentId == 0L) {
                roots.add(node);
            } else {
                parent.getChildren().add(node);
            }
        }
        return roots;
    }

    /**
     * 查询未删除权限实体
     *
     * @param id 权限ID
     * @return 权限实体
     */
    private SysPermission getEnabledEntity(Long id) {
        SysPermission permission = sysPermissionMapper.selectOne(Wrappers.<SysPermission>lambdaQuery()
                .eq(SysPermission::getId, id)
                .eq(SysPermission::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (permission == null) {
            throw new UserBizException("权限不存在");
        }
        return permission;
    }
}
