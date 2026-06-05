package com.very.blog.content.controller.category;

import com.very.blog.common.core.result.PageResult;
import com.very.blog.common.core.result.Result;
import com.very.blog.common.web.annotation.OperationLog;
import com.very.blog.common.web.enums.OperationContentEnum;
import com.very.blog.common.web.enums.OperationModuleEnum;
import com.very.blog.content.dto.category.CategoryCreateDTO;
import com.very.blog.content.dto.category.CategoryDeleteDTO;
import com.very.blog.content.dto.category.CategoryPageDTO;
import com.very.blog.content.dto.category.CategoryUpdateDTO;
import com.very.blog.content.service.category.CategoryService;
import com.very.blog.content.vo.category.CategoryTreeVO;
import com.very.blog.content.vo.category.CategoryVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类管理控制器
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    /**
     * 分类服务
     */
    private final CategoryService categoryService;

    /**
     * 构造分类管理控制器
     *
     * @param categoryService 分类服务
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 创建分类
     *
     * @param dto 分类创建入参
     * @return 分类信息
     */
    @OperationLog(module = OperationModuleEnum.CATEGORY, content = OperationContentEnum.CREATE)
    @PostMapping("/create")
    public Result<CategoryVO> create(@Valid @RequestBody CategoryCreateDTO dto) {
        return Result.success(categoryService.create(dto));
    }

    /**
     * 更新分类
     *
     * @param dto 分类更新入参
     * @return 分类信息
     */
    @OperationLog(module = OperationModuleEnum.CATEGORY, content = OperationContentEnum.UPDATE)
    @PostMapping("/update")
    public Result<CategoryVO> update(@Valid @RequestBody CategoryUpdateDTO dto) {
        return Result.success(categoryService.update(dto));
    }

    /**
     * 删除分类
     *
     * @param dto 分类删除入参
     * @return 删除结果
     */
    @OperationLog(module = OperationModuleEnum.CATEGORY, content = OperationContentEnum.DELETE)
    @PostMapping("/delete")
    public Result<Void> delete(@Valid @RequestBody CategoryDeleteDTO dto) {
        categoryService.delete(dto);
        return Result.success();
    }

    /**
     * 分页查询分类
     *
     * @param dto 分类分页查询入参
     * @return 分类分页列表
     */
    @PostMapping("/page")
    public Result<PageResult<CategoryVO>> page(@Valid @RequestBody CategoryPageDTO dto) {
        return Result.success(categoryService.page(dto));
    }

    /**
     * 根据分类ID查询分类
     *
     * @param id 分类ID
     * @return 分类信息
     */
    @GetMapping("/{id}")
    public Result<CategoryVO> getById(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }

    /**
     * 查询分类树
     *
     * @return 分类树列表
     */
    @GetMapping("/tree")
    public Result<List<CategoryTreeVO>> tree() {
        return Result.success(categoryService.tree());
    }
}
