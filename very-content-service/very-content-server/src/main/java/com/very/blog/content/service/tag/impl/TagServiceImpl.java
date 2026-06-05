package com.very.blog.content.service.tag.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.very.blog.common.core.result.PageResult;
import com.very.blog.content.convert.tag.TagConvert;
import com.very.blog.content.dto.tag.TagCreateDTO;
import com.very.blog.content.dto.tag.TagDeleteDTO;
import com.very.blog.content.dto.tag.TagPageDTO;
import com.very.blog.content.dto.tag.TagUpdateDTO;
import com.very.blog.content.dto.tag.RpcTagListDTO;
import com.very.blog.content.dto.tag.RpcTagQueryDTO;
import com.very.blog.content.entity.article.BlogArticleTag;
import com.very.blog.content.entity.tag.BlogTag;
import com.very.blog.content.enums.common.DeletedStatusEnum;
import com.very.blog.content.enums.common.EnableStatusEnum;
import com.very.blog.content.exception.common.ContentBizException;
import com.very.blog.content.mapper.article.BlogArticleTagMapper;
import com.very.blog.content.mapper.tag.BlogTagMapper;
import com.very.blog.content.service.tag.TagService;
import com.very.blog.content.vo.tag.RpcTagVO;
import com.very.blog.content.vo.tag.TagVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 标签服务实现
 */
@Service
public class TagServiceImpl implements TagService {

    /**
     * 文章标签Mapper
     */
    private final BlogTagMapper blogTagMapper;

    /**
     * 文章标签关联Mapper
     */
    private final BlogArticleTagMapper blogArticleTagMapper;

    /**
     * 构造标签服务实现
     *
     * @param blogTagMapper 文章标签Mapper
     * @param blogArticleTagMapper 文章标签关联Mapper
     */
    public TagServiceImpl(BlogTagMapper blogTagMapper, BlogArticleTagMapper blogArticleTagMapper) {
        this.blogTagMapper = blogTagMapper;
        this.blogArticleTagMapper = blogArticleTagMapper;
    }

    /**
     * 创建标签
     *
     * @param dto 标签创建入参
     * @return 标签信息
     */
    @Override
    public TagVO create(TagCreateDTO dto) {
        checkTagCode(dto.getTagCode(), null);
        BlogTag entity = TagConvert.toEntity(dto);
        entity.setDeleted(DeletedStatusEnum.NOT_DELETED.getCode());
        blogTagMapper.insert(entity);
        return TagConvert.toVO(entity);
    }

    /**
     * 更新标签
     *
     * @param dto 标签更新入参
     * @return 标签信息
     */
    @Override
    public TagVO update(TagUpdateDTO dto) {
        getEnabledEntity(dto.getId());
        checkTagCode(dto.getTagCode(), dto.getId());
        blogTagMapper.updateById(TagConvert.toEntity(dto));
        return getById(dto.getId());
    }

    /**
     * 删除标签
     *
     * @param dto 标签删除入参
     */
    @Override
    public void delete(TagDeleteDTO dto) {
        BlogTag entity = getEnabledEntity(dto.getId());
        long articleCount = blogArticleTagMapper.selectCount(Wrappers.<BlogArticleTag>lambdaQuery()
                .eq(BlogArticleTag::getTagId, dto.getId()));
        if (articleCount > 0) {
            throw new ContentBizException("标签已被文章使用，不能删除");
        }
        entity.setDeleted(DeletedStatusEnum.DELETED.getCode());
        blogTagMapper.updateById(entity);
    }

    /**
     * 分页查询标签
     *
     * @param dto 标签分页查询入参
     * @return 标签分页列表
     */
    @Override
    public PageResult<TagVO> page(TagPageDTO dto) {
        Page<BlogTag> page = blogTagMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()),
                Wrappers.<BlogTag>lambdaQuery()
                        .eq(BlogTag::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                        .and(isNotBlank(dto.getKeyword()), wrapper -> wrapper
                                .like(BlogTag::getTagCode, dto.getKeyword())
                                .or()
                                .like(BlogTag::getTagName, dto.getKeyword()))
                        .orderByAsc(BlogTag::getSortOrder)
                        .orderByDesc(BlogTag::getId));
        return PageResult.of(page.getTotal(), page.getRecords().stream()
                .map(TagConvert::toVO)
                .toList());
    }

    /**
     * 根据标签ID查询标签
     *
     * @param id 标签ID
     * @return 标签信息
     */
    @Override
    public TagVO getById(Long id) {
        return TagConvert.toVO(getEnabledEntity(id));
    }

    /**
     * 查询标签列表
     *
     * @return 标签列表
     */
    @Override
    public List<TagVO> list() {
        return blogTagMapper.selectList(Wrappers.<BlogTag>lambdaQuery()
                        .eq(BlogTag::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                        .eq(BlogTag::getStatus, EnableStatusEnum.ENABLED.getCode())
                        .orderByAsc(BlogTag::getSortOrder)
                        .orderByDesc(BlogTag::getId))
                .stream()
                .map(TagConvert::toVO)
                .toList();
    }

    /**
     * 查询RPC标签详情
     *
     * @param dto RPC标签查询入参
     * @return RPC标签信息
     */
    @Override
    public RpcTagVO getRpcTag(RpcTagQueryDTO dto) {
        return TagConvert.toRpcVO(getRpcTagEntity(dto.getId(), dto.getEnabledOnly()));
    }

    /**
     * 查询RPC标签列表
     *
     * @param dto RPC标签列表查询入参
     * @return RPC标签列表
     */
    @Override
    public List<RpcTagVO> listRpcTags(RpcTagListDTO dto) {
        List<Long> tagIds = dto.getIds().stream().distinct().toList();
        Map<Long, BlogTag> tagMap = blogTagMapper.selectList(Wrappers.<BlogTag>lambdaQuery()
                        .in(BlogTag::getId, tagIds)
                        .eq(BlogTag::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                        .eq(Boolean.TRUE.equals(dto.getEnabledOnly()), BlogTag::getStatus, EnableStatusEnum.ENABLED.getCode()))
                .stream()
                .collect(Collectors.toMap(BlogTag::getId, Function.identity()));
        return tagIds.stream()
                .map(tagMap::get)
                .filter(tag -> tag != null)
                .map(TagConvert::toRpcVO)
                .toList();
    }

    /**
     * 校验标签编码
     *
     * @param tagCode 标签编码
     * @param currentId 当前标签ID
     */
    private void checkTagCode(String tagCode, Long currentId) {
        long count = blogTagMapper.selectCount(Wrappers.<BlogTag>lambdaQuery()
                .eq(BlogTag::getTagCode, tagCode)
                .ne(currentId != null, BlogTag::getId, currentId)
                .eq(BlogTag::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (count > 0) {
            throw new ContentBizException("标签编码已存在");
        }
    }

    /**
     * 查询未删除标签实体
     *
     * @param id 标签ID
     * @return 标签实体
     */
    private BlogTag getEnabledEntity(Long id) {
        BlogTag tag = blogTagMapper.selectOne(Wrappers.<BlogTag>lambdaQuery()
                .eq(BlogTag::getId, id)
                .eq(BlogTag::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (tag == null) {
            throw new ContentBizException("标签不存在");
        }
        return tag;
    }

    /**
     * 查询RPC标签实体
     *
     * @param id 标签ID
     * @param enabledOnly 是否只查询启用标签
     * @return 标签实体
     */
    private BlogTag getRpcTagEntity(Long id, Boolean enabledOnly) {
        BlogTag tag = blogTagMapper.selectOne(Wrappers.<BlogTag>lambdaQuery()
                .eq(BlogTag::getId, id)
                .eq(BlogTag::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                .eq(Boolean.TRUE.equals(enabledOnly), BlogTag::getStatus, EnableStatusEnum.ENABLED.getCode()));
        if (tag == null) {
            throw new ContentBizException("标签不存在");
        }
        return tag;
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
