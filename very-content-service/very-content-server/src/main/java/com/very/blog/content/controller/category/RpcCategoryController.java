package com.very.blog.content.controller.category;

import com.very.blog.common.core.result.Result;
import com.very.blog.content.dto.category.RpcCategoryListDTO;
import com.very.blog.content.dto.category.RpcCategoryQueryDTO;
import com.very.blog.content.feign.category.RpcCategoryFeignClient;
import com.very.blog.content.service.category.CategoryService;
import com.very.blog.content.vo.category.RpcCategoryVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RPC分类控制器
 */
@RestController
public class RpcCategoryController implements RpcCategoryFeignClient {

    /**
     * 分类服务
     */
    private final CategoryService categoryService;

    /**
     * 构造RPC分类控制器
     *
     * @param categoryService 分类服务
     */
    public RpcCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 查询RPC分类详情
     *
     * @param dto RPC分类查询入参
     * @return RPC分类信息
     */
    @Override
    public Result<RpcCategoryVO> getCategory(@Valid @RequestBody RpcCategoryQueryDTO dto) {
        return Result.success(categoryService.getRpcCategory(dto));
    }

    /**
     * 查询RPC分类列表
     *
     * @param dto RPC分类列表查询入参
     * @return RPC分类列表
     */
    @Override
    public Result<List<RpcCategoryVO>> listCategories(@Valid @RequestBody RpcCategoryListDTO dto) {
        return Result.success(categoryService.listRpcCategories(dto));
    }
}
