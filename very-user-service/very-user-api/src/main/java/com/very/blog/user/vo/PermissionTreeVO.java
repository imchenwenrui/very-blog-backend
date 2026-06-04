package com.very.blog.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限树出参
 */
@Data
public class PermissionTreeVO implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    private Long id;

    /**
     * 父级权限ID
     */
    private Long parentId;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限类型
     */
    private Integer permissionType;

    /**
     * 前端路由或接口路径
     */
    private String path;

    /**
     * 接口请求方法
     */
    private String httpMethod;

    /**
     * 权限状态
     */
    private Integer status;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 子级权限列表
     */
    private List<PermissionTreeVO> children = new ArrayList<>();
}
