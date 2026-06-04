package com.very.blog.user.controller;

import com.very.blog.common.core.result.PageResult;
import com.very.blog.common.core.result.Result;
import com.very.blog.common.web.annotation.OperationLog;
import com.very.blog.common.web.enums.OperationContentEnum;
import com.very.blog.common.web.enums.OperationModuleEnum;
import com.very.blog.user.dto.UserCreateDTO;
import com.very.blog.user.dto.UserPageDTO;
import com.very.blog.user.dto.UserUpdateDTO;
import com.very.blog.user.service.UserService;
import com.very.blog.user.vo.UserVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 用户服务
     */
    private final UserService userService;

    /**
     * 构造用户管理控制器
     *
     * @param userService 用户服务
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 创建用户
     *
     * @param dto 用户创建入参
     * @return 用户信息
     */
    @OperationLog(module = OperationModuleEnum.USER, content = OperationContentEnum.CREATE)
    @PostMapping("/create")
    public Result<UserVO> create(@Valid @RequestBody UserCreateDTO dto) {
        return Result.success(userService.create(dto));
    }

    /**
     * 更新用户
     *
     * @param dto 用户更新入参
     * @return 用户信息
     */
    @OperationLog(module = OperationModuleEnum.USER, content = OperationContentEnum.UPDATE)
    @PostMapping("/update")
    public Result<UserVO> update(@Valid @RequestBody UserUpdateDTO dto) {
        return Result.success(userService.update(dto));
    }

    /**
     * 根据用户ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public Result<UserVO> getById(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }

    /**
     * 分页查询用户列表
     *
     * @param dto 用户分页查询入参
     * @return 用户分页列表
     */
    @PostMapping("/page")
    public Result<PageResult<UserVO>> page(@Valid @RequestBody UserPageDTO dto) {
        return Result.success(userService.page(dto));
    }
}
