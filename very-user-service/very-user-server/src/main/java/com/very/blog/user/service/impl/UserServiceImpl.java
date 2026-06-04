package com.very.blog.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.very.blog.common.core.result.PageResult;
import com.very.blog.user.convert.UserConvert;
import com.very.blog.user.dto.UserCreateDTO;
import com.very.blog.user.dto.UserPageDTO;
import com.very.blog.user.dto.UserUpdateDTO;
import com.very.blog.user.entity.SysUser;
import com.very.blog.user.enums.DeletedStatusEnum;
import com.very.blog.user.exception.UserBizException;
import com.very.blog.user.mapper.SysUserMapper;
import com.very.blog.user.service.UserService;
import com.very.blog.user.vo.UserVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 系统用户Mapper
     */
    private final SysUserMapper sysUserMapper;

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 构造用户服务实现
     *
     * @param sysUserMapper 系统用户Mapper
     * @param passwordEncoder 密码编码器
     */
    public UserServiceImpl(SysUserMapper sysUserMapper, PasswordEncoder passwordEncoder) {
        this.sysUserMapper = sysUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 创建用户
     *
     * @param dto 用户创建入参
     * @return 用户信息
     */
    @Override
    public UserVO create(UserCreateDTO dto) {
        long count = sysUserMapper.selectCount(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, dto.getUsername())
                .eq(SysUser::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (count > 0) {
            throw new UserBizException("登录账号已存在");
        }

        SysUser entity = UserConvert.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setDeleted(DeletedStatusEnum.NOT_DELETED.getCode());
        sysUserMapper.insert(entity);
        return UserConvert.toVO(entity);
    }

    /**
     * 更新用户
     *
     * @param dto 用户更新入参
     * @return 用户信息
     */
    @Override
    public UserVO update(UserUpdateDTO dto) {
        SysUser oldUser = getEnabledEntity(dto.getId());
        SysUser entity = UserConvert.toEntity(dto);
        entity.setUsername(oldUser.getUsername());
        sysUserMapper.updateById(entity);
        return getById(dto.getId());
    }

    /**
     * 根据用户ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserVO getById(Long id) {
        return UserConvert.toVO(getEnabledEntity(id));
    }

    /**
     * 分页查询用户列表
     *
     * @param dto 用户分页查询入参
     * @return 用户分页列表
     */
    @Override
    public PageResult<UserVO> page(UserPageDTO dto) {
        Page<SysUser> page = sysUserMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()),
                Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                        .and(dto.getKeyword() != null && !dto.getKeyword().isBlank(), wrapper -> wrapper
                                .like(SysUser::getUsername, dto.getKeyword())
                                .or()
                                .like(SysUser::getNickname, dto.getKeyword()))
                        .orderByDesc(SysUser::getId));
        return PageResult.of(page.getTotal(), page.getRecords().stream()
                .map(UserConvert::toVO)
                .toList());
    }

    /**
     * 查询未删除用户实体
     *
     * @param id 用户ID
     * @return 用户实体
     */
    private SysUser getEnabledEntity(Long id) {
        SysUser user = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getId, id)
                .eq(SysUser::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (user == null) {
            throw new UserBizException("用户不存在");
        }
        return user;
    }
}
