package com.very.blog.user.convert;

import com.very.blog.user.dto.UserCreateDTO;
import com.very.blog.user.dto.UserUpdateDTO;
import com.very.blog.user.entity.SysUser;
import com.very.blog.user.vo.UserVO;

/**
 * 用户对象转换工具
 */
public final class UserConvert {

    /**
     * 工具类构造方法
     */
    private UserConvert() {
    }

    /**
     * 将用户创建入参转换为用户实体
     *
     * @param dto 用户创建入参
     * @return 用户实体
     */
    public static SysUser toEntity(UserCreateDTO dto) {
        SysUser entity = new SysUser();
        entity.setUsername(dto.getUsername());
        entity.setNickname(dto.getNickname());
        entity.setAvatar(dto.getAvatar());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setUserType(dto.getUserType());
        entity.setStatus(dto.getStatus());
        entity.setRemark(dto.getRemark());
        return entity;
    }

    /**
     * 将用户更新入参转换为用户实体
     *
     * @param dto 用户更新入参
     * @return 用户实体
     */
    public static SysUser toEntity(UserUpdateDTO dto) {
        SysUser entity = new SysUser();
        entity.setId(dto.getId());
        entity.setNickname(dto.getNickname());
        entity.setAvatar(dto.getAvatar());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setUserType(dto.getUserType());
        entity.setStatus(dto.getStatus());
        entity.setRemark(dto.getRemark());
        return entity;
    }

    /**
     * 将用户实体转换为用户出参
     *
     * @param entity 用户实体
     * @return 用户出参
     */
    public static UserVO toVO(SysUser entity) {
        if (entity == null) {
            return null;
        }
        UserVO vo = new UserVO();
        vo.setId(entity.getId());
        vo.setUsername(entity.getUsername());
        vo.setNickname(entity.getNickname());
        vo.setAvatar(entity.getAvatar());
        vo.setEmail(entity.getEmail());
        vo.setPhone(entity.getPhone());
        vo.setUserType(entity.getUserType());
        vo.setStatus(entity.getStatus());
        vo.setLastLoginTime(entity.getLastLoginTime());
        vo.setLastLoginIp(entity.getLastLoginIp());
        vo.setRemark(entity.getRemark());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }
}
