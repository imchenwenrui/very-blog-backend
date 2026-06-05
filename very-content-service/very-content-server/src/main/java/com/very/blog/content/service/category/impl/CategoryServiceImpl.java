package com.very.blog.content.service.category.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.very.blog.common.core.result.PageResult;
import com.very.blog.content.convert.category.CategoryConvert;
import com.very.blog.content.dto.category.CategoryCreateDTO;
import com.very.blog.content.dto.category.CategoryDeleteDTO;
import com.very.blog.content.dto.category.CategoryPageDTO;
import com.very.blog.content.dto.category.CategoryUpdateDTO;
import com.very.blog.content.dto.category.RpcCategoryListDTO;
import com.very.blog.content.dto.category.RpcCategoryQueryDTO;
import com.very.blog.content.entity.article.BlogArticle;
import com.very.blog.content.entity.category.BlogCategory;
import com.very.blog.content.enums.common.DeletedStatusEnum;
import com.very.blog.content.enums.common.EnableStatusEnum;
import com.very.blog.content.exception.common.ContentBizException;
import com.very.blog.content.mapper.article.BlogArticleMapper;
import com.very.blog.content.mapper.category.BlogCategoryMapper;
import com.very.blog.content.service.category.CategoryService;
import com.very.blog.content.vo.category.CategoryTreeVO;
import com.very.blog.content.vo.category.CategoryVO;
import com.very.blog.content.vo.category.RpcCategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类服务实现
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    /**
     * 根分类ID
     */
    private static final Long ROOT_PARENT_ID = 0L;

    /**
     * 文章分类Mapper
     */
    private final BlogCategoryMapper blogCategoryMapper;

    /**
     * 文章Mapper
     */
    private final BlogArticleMapper blogArticleMapper;

    /**
     * 构造分类服务实现
     *
     * @param blogCategoryMapper 文章分类Mapper
     * @param blogArticleMapper 文章Mapper
     */
    public CategoryServiceImpl(BlogCategoryMapper blogCategoryMapper, BlogArticleMapper blogArticleMapper) {
        this.blogCategoryMapper = blogCategoryMapper;
        this.blogArticleMapper = blogArticleMapper;
    }

    /**
     * 创建分类
     *
     * @param dto 分类创建入参
     * @return 分类信息
     */
    @Override
    public CategoryVO create(CategoryCreateDTO dto) {
        checkCategoryCode(dto.getCategoryCode(), null);
        BlogCategory entity = CategoryConvert.toEntity(dto);
        entity.setParentId(getParentId(dto.getParentId()));
        entity.setDeleted(DeletedStatusEnum.NOT_DELETED.getCode());
        blogCategoryMapper.insert(entity);
        return CategoryConvert.toVO(entity);
    }

    /**
     * 更新分类
     *
     * @param dto 分类更新入参
     * @return 分类信息
     */
    @Override
    public CategoryVO update(CategoryUpdateDTO dto) {
        getEnabledEntity(dto.getId());
        checkCategoryCode(dto.getCategoryCode(), dto.getId());
        BlogCategory entity = CategoryConvert.toEntity(dto);
        entity.setParentId(getParentId(dto.getParentId()));
        blogCategoryMapper.updateById(entity);
        return getById(dto.getId());
    }

    /**
     * 删除分类
     *
     * @param dto 分类删除入参
     */
    @Override
    public void delete(CategoryDeleteDTO dto) {
        BlogCategory entity = getEnabledEntity(dto.getId());
        long childCount = blogCategoryMapper.selectCount(Wrappers.<BlogCategory>lambdaQuery()
                .eq(BlogCategory::getParentId, dto.getId())
                .eq(BlogCategory::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (childCount > 0) {
            throw new ContentBizException("分类存在子分类，不能删除");
        }
        long articleCount = blogArticleMapper.selectCount(Wrappers.<BlogArticle>lambdaQuery()
                .eq(BlogArticle::getCategoryId, dto.getId())
                .eq(BlogArticle::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (articleCount > 0) {
            throw new ContentBizException("分类已被文章使用，不能删除");
        }
        entity.setDeleted(DeletedStatusEnum.DELETED.getCode());
        blogCategoryMapper.updateById(entity);
    }

    /**
     * 分页查询分类
     *
     * @param dto 分类分页查询入参
     * @return 分类分页列表
     */
    @Override
    public PageResult<CategoryVO> page(CategoryPageDTO dto) {
        Page<BlogCategory> page = blogCategoryMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()),
                Wrappers.<BlogCategory>lambdaQuery()
                        .eq(BlogCategory::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                        .and(isNotBlank(dto.getKeyword()), wrapper -> wrapper
                                .like(BlogCategory::getCategoryCode, dto.getKeyword())
                                .or()
                                .like(BlogCategory::getCategoryName, dto.getKeyword()))
                        .orderByAsc(BlogCategory::getSortOrder)
                        .orderByDesc(BlogCategory::getId));
        return PageResult.of(page.getTotal(), page.getRecords().stream()
                .map(CategoryConvert::toVO)
                .toList());
    }

    /**
     * 根据分类ID查询分类
     *
     * @param id 分类ID
     * @return 分类信息
     */
    @Override
    public CategoryVO getById(Long id) {
        return CategoryConvert.toVO(getEnabledEntity(id));
    }

    /**
     * 查询分类树
     *
     * @return 分类树列表
     */
    @Override
    public List<CategoryTreeVO> tree() {
        List<CategoryTreeVO> categories = blogCategoryMapper.selectList(Wrappers.<BlogCategory>lambdaQuery()
                        .eq(BlogCategory::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                        .orderByAsc(BlogCategory::getSortOrder)
                        .orderByDesc(BlogCategory::getId))
                .stream()
                .map(CategoryConvert::toTreeVO)
                .toList();
        Map<Long, List<CategoryTreeVO>> parentMap = categories.stream()
                .collect(Collectors.groupingBy(CategoryTreeVO::getParentId));
        categories.forEach(category -> category.setChildren(parentMap.getOrDefault(category.getId(), List.of())));
        return parentMap.getOrDefault(ROOT_PARENT_ID, List.of());
    }

    /**
     * 查询RPC分类详情
     *
     * @param dto RPC分类查询入参
     * @return RPC分类信息
     */
    @Override
    public RpcCategoryVO getRpcCategory(RpcCategoryQueryDTO dto) {
        return CategoryConvert.toRpcVO(getRpcCategoryEntity(dto.getId(), dto.getEnabledOnly()));
    }

    /**
     * 查询RPC分类列表
     *
     * @param dto RPC分类列表查询入参
     * @return RPC分类列表
     */
    @Override
    public List<RpcCategoryVO> listRpcCategories(RpcCategoryListDTO dto) {
        List<Long> categoryIds = dto.getIds().stream().distinct().toList();
        Map<Long, BlogCategory> categoryMap = blogCategoryMapper.selectList(Wrappers.<BlogCategory>lambdaQuery()
                        .in(BlogCategory::getId, categoryIds)
                        .eq(BlogCategory::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                        .eq(Boolean.TRUE.equals(dto.getEnabledOnly()), BlogCategory::getStatus, EnableStatusEnum.ENABLED.getCode()))
                .stream()
                .collect(Collectors.toMap(BlogCategory::getId, Function.identity()));
        return categoryIds.stream()
                .map(categoryMap::get)
                .filter(category -> category != null)
                .map(CategoryConvert::toRpcVO)
                .toList();
    }

    /**
     * 校验分类编码
     *
     * @param categoryCode 分类编码
     * @param currentId 当前分类ID
     */
    private void checkCategoryCode(String categoryCode, Long currentId) {
        long count = blogCategoryMapper.selectCount(Wrappers.<BlogCategory>lambdaQuery()
                .eq(BlogCategory::getCategoryCode, categoryCode)
                .ne(currentId != null, BlogCategory::getId, currentId)
                .eq(BlogCategory::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (count > 0) {
            throw new ContentBizException("分类编码已存在");
        }
    }

    /**
     * 查询未删除分类实体
     *
     * @param id 分类ID
     * @return 分类实体
     */
    private BlogCategory getEnabledEntity(Long id) {
        BlogCategory category = blogCategoryMapper.selectOne(Wrappers.<BlogCategory>lambdaQuery()
                .eq(BlogCategory::getId, id)
                .eq(BlogCategory::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (category == null) {
            throw new ContentBizException("分类不存在");
        }
        return category;
    }

    /**
     * 查询RPC分类实体
     *
     * @param id 分类ID
     * @param enabledOnly 是否只查询启用分类
     * @return 分类实体
     */
    private BlogCategory getRpcCategoryEntity(Long id, Boolean enabledOnly) {
        BlogCategory category = blogCategoryMapper.selectOne(Wrappers.<BlogCategory>lambdaQuery()
                .eq(BlogCategory::getId, id)
                .eq(BlogCategory::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                .eq(Boolean.TRUE.equals(enabledOnly), BlogCategory::getStatus, EnableStatusEnum.ENABLED.getCode()));
        if (category == null) {
            throw new ContentBizException("分类不存在");
        }
        return category;
    }

    /**
     * 获取父级分类ID
     *
     * @param parentId 父级分类ID
     * @return 父级分类ID
     */
    private Long getParentId(Long parentId) {
        return parentId == null ? ROOT_PARENT_ID : parentId;
    }

    /**
     * 判断字符串是否非空
     *
     * @param value 字符串
     * @return 是否非空
     */
    private boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }
}
