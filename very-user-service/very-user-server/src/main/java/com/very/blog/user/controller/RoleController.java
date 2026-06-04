package com.very.blog.user.controller;

import com.very.blog.common.core.result.PageResult;
import com.very.blog.common.core.result.Result;
import com.very.blog.common.web.annotation.OperationLog;
import com.very.blog.common.web.enums.OperationContentEnum;
import com.very.blog.common.web.enums.OperationModuleEnum;
import com.very.blog.user.dto.RoleCreateDTO;
import com.very.blog.user.dto.RolePageDTO;
import com.very.blog.user.dto.RoleUpdateDTO;
import com.very.blog.user.service.RoleService;
import com.very.blog.user.vo.RoleVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    /**
     * 角色服务
     */
    private final RoleService roleService;

    /**
     * 构造角色管理控制器
     *
     * @param roleService 角色服务
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 创建角色
     *
     * @param dto 角色创建入参
     * @return 角色信息
     */
    @OperationLog(module = OperationModuleEnum.ROLE, content = OperationContentEnum.CREATE)
    @PostMapping("/create")
    public Result<RoleVO> create(@Valid @RequestBody RoleCreateDTO dto) {
        return Result.success(roleService.create(dto));
    }

    /**
     * 更新角色
     *
     * @param dto 角色更新入参
     * @return 角色信息
     */
    @OperationLog(module = OperationModuleEnum.ROLE, content = OperationContentEnum.UPDATE)
    @PostMapping("/update")
    public Result<RoleVO> update(@Valid @RequestBody RoleUpdateDTO dto) {
        return Result.success(roleService.update(dto));
    }

    /**
     * 根据角色ID查询角色
     *
     * @param id 角色ID
     * @return 角色信息
     */
    @GetMapping("/{id}")
    public Result<RoleVO> getById(@PathVariable Long id) {
        return Result.success(roleService.getById(id));
    }

    /**
     * 分页查询角色列表
     *
     * @param dto 角色分页查询入参
     * @return 角色分页列表
     */
    @PostMapping("/page")
    public Result<PageResult<RoleVO>> page(@Valid @RequestBody RolePageDTO dto) {
        return Result.success(roleService.page(dto));
    }
}
