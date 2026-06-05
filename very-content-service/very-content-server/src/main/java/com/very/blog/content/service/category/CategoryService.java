package com.very.blog.content.service.category;

import com.very.blog.common.core.result.PageResult;
import com.very.blog.content.dto.category.CategoryCreateDTO;
import com.very.blog.content.dto.category.CategoryDeleteDTO;
import com.very.blog.content.dto.category.CategoryPageDTO;
import com.very.blog.content.dto.category.CategoryUpdateDTO;
import com.very.blog.content.dto.category.RpcCategoryListDTO;
import com.very.blog.content.dto.category.RpcCategoryQueryDTO;
import com.very.blog.content.vo.category.CategoryTreeVO;
import com.very.blog.content.vo.category.CategoryVO;
import com.very.blog.content.vo.category.RpcCategoryVO;

import java.util.List;

/**
 * 分类服务
 */
public interface CategoryService {

    /**
     * 创建分类
     *
     * @param dto 分类创建入参
     * @return 分类信息
     */
    CategoryVO create(CategoryCreateDTO dto);

    /**
     * 更新分类
     *
     * @param dto 分类更新入参
     * @return 分类信息
     */
    CategoryVO update(CategoryUpdateDTO dto);

    /**
     * 删除分类
     *
     * @param dto 分类删除入参
     */
    void delete(CategoryDeleteDTO dto);

    /**
     * 分页查询分类
     *
     * @param dto 分类分页查询入参
     * @return 分类分页列表
     */
    PageResult<CategoryVO> page(CategoryPageDTO dto);

    /**
     * 根据分类ID查询分类
     *
     * @param id 分类ID
     * @return 分类信息
     */
    CategoryVO getById(Long id);

    /**
     * 查询分类树
     *
     * @return 分类树列表
     */
    List<CategoryTreeVO> tree();

    /**
     * 查询RPC分类详情
     *
     * @param dto RPC分类查询入参
     * @return RPC分类信息
     */
    RpcCategoryVO getRpcCategory(RpcCategoryQueryDTO dto);

    /**
     * 查询RPC分类列表
     *
     * @param dto RPC分类列表查询入参
     * @return RPC分类列表
     */
    List<RpcCategoryVO> listRpcCategories(RpcCategoryListDTO dto);
}
