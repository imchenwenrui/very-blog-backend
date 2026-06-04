package com.very.blog.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.very.blog.user.entity.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限关联Mapper
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {
}
