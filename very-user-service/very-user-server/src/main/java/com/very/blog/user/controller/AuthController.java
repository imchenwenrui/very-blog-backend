package com.very.blog.user.controller;

import com.very.blog.common.core.result.Result;
import com.very.blog.common.web.annotation.OperationLog;
import com.very.blog.common.web.enums.OperationContentEnum;
import com.very.blog.common.web.enums.OperationModuleEnum;
import com.very.blog.user.dto.LoginDTO;
import com.very.blog.user.service.AuthService;
import com.very.blog.user.vo.LoginVO;
import com.very.blog.user.vo.UserVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录认证控制器
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * 登录认证服务
     */
    private final AuthService authService;

    /**
     * 构造登录认证控制器
     *
     * @param authService 登录认证服务
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 用户登录
     *
     * @param dto 登录入参
     * @return 登录结果
     */
    @OperationLog(module = OperationModuleEnum.AUTH, content = OperationContentEnum.LOGIN)
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO dto) {
        return Result.success(authService.login(dto));
    }

    /**
     * 用户登出
     *
     * @return 登出结果
     */
    @OperationLog(module = OperationModuleEnum.AUTH, content = OperationContentEnum.LOGOUT)
    @PostMapping("/logout")
    public Result<Void> logout() {
        authService.logout();
        return Result.success();
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户信息
     */
    @GetMapping("/current")
    public Result<UserVO> currentUser() {
        return Result.success(authService.currentUser());
    }
}
