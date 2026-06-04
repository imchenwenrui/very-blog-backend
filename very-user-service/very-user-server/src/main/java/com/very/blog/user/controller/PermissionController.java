package com.very.blog.user.controller;

import com.very.blog.common.core.result.Result;
import com.very.blog.common.web.annotation.OperationLog;
import com.very.blog.common.web.enums.OperationContentEnum;
import com.very.blog.common.web.enums.OperationModuleEnum;
import com.very.blog.user.dto.PermissionCreateDTO;
import com.very.blog.user.dto.PermissionUpdateDTO;
import com.very.blog.user.service.PermissionService;
import com.very.blog.user.vo.PermissionTreeVO;
import com.very.blog.user.vo.PermissionVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限管理控制器
 */
@RestController
@RequestMapping("/permissions")
public class PermissionController {

    /**
     * 权限服务
     */
    private final PermissionService permissionService;

    /**
     * 构造权限管理控制器
     *
     * @param permissionService 权限服务
     */
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 创建权限
     *
     * @param dto 权限创建入参
     * @return 权限信息
     */
    @OperationLog(module = OperationModuleEnum.PERMISSION, content = OperationContentEnum.CREATE)
    @PostMapping("/create")
    public Result<PermissionVO> create(@Valid @RequestBody PermissionCreateDTO dto) {
        return Result.success(permissionService.create(dto));
    }

    /**
     * 更新权限
     *
     * @param dto 权限更新入参
     * @return 权限信息
     */
    @OperationLog(module = OperationModuleEnum.PERMISSION, content = OperationContentEnum.UPDATE)
    @PostMapping("/update")
    public Result<PermissionVO> update(@Valid @RequestBody PermissionUpdateDTO dto) {
        return Result.success(permissionService.update(dto));
    }

    /**
     * 根据权限ID查询权限
     *
     * @param id 权限ID
     * @return 权限信息
     */
    @GetMapping("/{id}")
    public Result<PermissionVO> getById(@PathVariable Long id) {
        return Result.success(permissionService.getById(id));
    }

    /**
     * 查询权限树
     *
     * @return 权限树列表
     */
    @GetMapping("/tree")
    public Result<List<PermissionTreeVO>> tree() {
        return Result.success(permissionService.tree());
    }
}
