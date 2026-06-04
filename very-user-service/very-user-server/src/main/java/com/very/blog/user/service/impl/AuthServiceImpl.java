package com.very.blog.user.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.very.blog.user.convert.UserConvert;
import com.very.blog.user.dto.LoginDTO;
import com.very.blog.user.entity.SysPermission;
import com.very.blog.user.entity.SysRole;
import com.very.blog.user.entity.SysRolePermission;
import com.very.blog.user.entity.SysUser;
import com.very.blog.user.entity.SysUserRole;
import com.very.blog.user.enums.DeletedStatusEnum;
import com.very.blog.user.enums.EnableStatusEnum;
import com.very.blog.user.exception.UserBizException;
import com.very.blog.user.mapper.SysPermissionMapper;
import com.very.blog.user.mapper.SysRoleMapper;
import com.very.blog.user.mapper.SysRolePermissionMapper;
import com.very.blog.user.mapper.SysUserMapper;
import com.very.blog.user.mapper.SysUserRoleMapper;
import com.very.blog.user.service.AuthService;
import com.very.blog.user.vo.LoginVO;
import com.very.blog.user.vo.UserVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 登录认证服务实现
 */
@Service
public class AuthServiceImpl implements AuthService {

    /**
     * 系统用户Mapper
     */
    private final SysUserMapper sysUserMapper;

    /**
     * 系统角色Mapper
     */
    private final SysRoleMapper sysRoleMapper;

    /**
     * 系统权限Mapper
     */
    private final SysPermissionMapper sysPermissionMapper;

    /**
     * 用户角色关联Mapper
     */
    private final SysUserRoleMapper sysUserRoleMapper;

    /**
     * 角色权限关联Mapper
     */
    private final SysRolePermissionMapper sysRolePermissionMapper;

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 构造登录认证服务实现
     *
     * @param sysUserMapper 系统用户Mapper
     * @param sysRoleMapper 系统角色Mapper
     * @param sysPermissionMapper 系统权限Mapper
     * @param sysUserRoleMapper 用户角色关联Mapper
     * @param sysRolePermissionMapper 角色权限关联Mapper
     * @param passwordEncoder 密码编码器
     */
    public AuthServiceImpl(SysUserMapper sysUserMapper,
                           SysRoleMapper sysRoleMapper,
                           SysPermissionMapper sysPermissionMapper,
                           SysUserRoleMapper sysUserRoleMapper,
                           SysRolePermissionMapper sysRolePermissionMapper,
                           PasswordEncoder passwordEncoder) {
        this.sysUserMapper = sysUserMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysPermissionMapper = sysPermissionMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysRolePermissionMapper = sysRolePermissionMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 用户登录
     *
     * @param dto 登录入参
     * @return 登录结果
     */
    @Override
    public LoginVO login(LoginDTO dto) {
        SysUser user = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, dto.getUsername())
                .eq(SysUser::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UserBizException("账号或密码错误");
        }
        if (!EnableStatusEnum.ENABLED.getCode().equals(user.getStatus())) {
            throw new UserBizException("账号已被禁用");
        }

        StpUtil.login(user.getId());
        user.setLastLoginTime(LocalDateTime.now());
        sysUserMapper.updateById(user);

        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        LoginVO vo = new LoginVO();
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setTokenName(tokenInfo.getTokenName());
        vo.setTokenValue(tokenInfo.getTokenValue());
        vo.setRoleCodes(getRoleCodes(user.getId()));
        vo.setPermissionCodes(getPermissionCodes(vo.getRoleCodes()));
        return vo;
    }

    /**
     * 用户登出
     */
    @Override
    public void logout() {
        StpUtil.logout();
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户信息
     */
    @Override
    public UserVO currentUser() {
        Long userId = StpUtil.getLoginIdAsLong();
        SysUser user = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getId, userId)
                .eq(SysUser::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (user == null) {
            throw new UserBizException("用户不存在");
        }
        return UserConvert.toVO(user);
    }

    /**
     * 查询用户拥有的角色编码列表
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    private List<String> getRoleCodes(Long userId) {
        List<Long> roleIds = sysUserRoleMapper.selectList(Wrappers.<SysUserRole>lambdaQuery()
                        .eq(SysUserRole::getUserId, userId))
                .stream()
                .map(SysUserRole::getRoleId)
                .toList();
        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                        .in(SysRole::getId, roleIds)
                        .eq(SysRole::getStatus, EnableStatusEnum.ENABLED.getCode())
                        .eq(SysRole::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()))
                .stream()
                .map(SysRole::getRoleCode)
                .toList();
    }

    /**
     * 查询角色拥有的权限编码列表
     *
     * @param roleCodes 角色编码列表
     * @return 权限编码列表
     */
    private List<String> getPermissionCodes(List<String> roleCodes) {
        if (roleCodes == null || roleCodes.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> roleIds = sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                        .in(SysRole::getRoleCode, roleCodes)
                        .eq(SysRole::getStatus, EnableStatusEnum.ENABLED.getCode())
                        .eq(SysRole::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()))
                .stream()
                .map(SysRole::getId)
                .toList();
        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> permissionIds = sysRolePermissionMapper.selectList(Wrappers.<SysRolePermission>lambdaQuery()
                        .in(SysRolePermission::getRoleId, roleIds))
                .stream()
                .map(SysRolePermission::getPermissionId)
                .toList();
        if (permissionIds.isEmpty()) {
            return Collections.emptyList();
        }

        return sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                        .in(SysPermission::getId, permissionIds)
                        .eq(SysPermission::getStatus, EnableStatusEnum.ENABLED.getCode())
                        .eq(SysPermission::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()))
                .stream()
                .map(SysPermission::getPermissionCode)
                .distinct()
                .toList();
    }
}
