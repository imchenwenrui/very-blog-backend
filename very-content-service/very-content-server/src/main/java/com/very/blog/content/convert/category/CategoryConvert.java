package com.very.blog.content.convert.category;

import com.very.blog.content.dto.category.CategoryCreateDTO;
import com.very.blog.content.dto.category.CategoryUpdateDTO;
import com.very.blog.content.entity.category.BlogCategory;
import com.very.blog.content.vo.category.CategoryTreeVO;
import com.very.blog.content.vo.category.CategoryVO;
import com.very.blog.content.vo.category.RpcCategoryVO;

/**
 * 分类对象转换工具
 */
public final class CategoryConvert {

    /**
     * 工具类构造方法
     */
    private CategoryConvert() {
    }

    /**
     * 将分类创建入参转换为分类实体
     *
     * @param dto 分类创建入参
     * @return 分类实体
     */
    public static BlogCategory toEntity(CategoryCreateDTO dto) {
        BlogCategory entity = new BlogCategory();
        entity.setParentId(dto.getParentId());
        entity.setCategoryCode(dto.getCategoryCode());
        entity.setCategoryName(dto.getCategoryName());
        entity.setDescription(dto.getDescription());
        entity.setSortOrder(dto.getSortOrder());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    /**
     * 将分类更新入参转换为分类实体
     *
     * @param dto 分类更新入参
     * @return 分类实体
     */
    public static BlogCategory toEntity(CategoryUpdateDTO dto) {
        BlogCategory entity = new BlogCategory();
        entity.setId(dto.getId());
        entity.setParentId(dto.getParentId());
        entity.setCategoryCode(dto.getCategoryCode());
        entity.setCategoryName(dto.getCategoryName());
        entity.setDescription(dto.getDescription());
        entity.setSortOrder(dto.getSortOrder());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    /**
     * 将分类实体转换为分类出参
     *
     * @param entity 分类实体
     * @return 分类出参
     */
    public static CategoryVO toVO(BlogCategory entity) {
        if (entity == null) {
            return null;
        }
        CategoryVO vo = new CategoryVO();
        vo.setId(entity.getId());
        vo.setParentId(entity.getParentId());
        vo.setCategoryCode(entity.getCategoryCode());
        vo.setCategoryName(entity.getCategoryName());
        vo.setDescription(entity.getDescription());
        vo.setSortOrder(entity.getSortOrder());
        vo.setStatus(entity.getStatus());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    /**
     * 将分类实体转换为分类树出参
     *
     * @param entity 分类实体
     * @return 分类树出参
     */
    public static CategoryTreeVO toTreeVO(BlogCategory entity) {
        CategoryTreeVO vo = new CategoryTreeVO();
        vo.setId(entity.getId());
        vo.setParentId(entity.getParentId());
        vo.setCategoryCode(entity.getCategoryCode());
        vo.setCategoryName(entity.getCategoryName());
        vo.setSortOrder(entity.getSortOrder());
        vo.setStatus(entity.getStatus());
        return vo;
    }

    /**
     * 将分类实体转换为RPC分类出参
     *
     * @param entity 分类实体
     * @return RPC分类出参
     */
    public static RpcCategoryVO toRpcVO(BlogCategory entity) {
        if (entity == null) {
            return null;
        }
        RpcCategoryVO vo = new RpcCategoryVO();
        vo.setId(entity.getId());
        vo.setParentId(entity.getParentId());
        vo.setCategoryCode(entity.getCategoryCode());
        vo.setCategoryName(entity.getCategoryName());
        vo.setStatus(entity.getStatus());
        return vo;
    }
}
