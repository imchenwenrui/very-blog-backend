package com.very.blog.content.service.article.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.very.blog.common.core.result.PageResult;
import com.very.blog.content.convert.article.ArticleConvert;
import com.very.blog.content.convert.tag.TagConvert;
import com.very.blog.content.dto.article.ArticleCreateDTO;
import com.very.blog.content.dto.article.ArticleDeleteDTO;
import com.very.blog.content.dto.article.ArticleOfflineDTO;
import com.very.blog.content.dto.article.ArticlePageDTO;
import com.very.blog.content.dto.article.ArticlePublishDTO;
import com.very.blog.content.dto.article.ArticleUpdateDTO;
import com.very.blog.content.dto.article.RpcArticleListDTO;
import com.very.blog.content.dto.article.RpcArticleQueryDTO;
import com.very.blog.content.entity.article.BlogArticle;
import com.very.blog.content.entity.article.BlogArticleTag;
import com.very.blog.content.entity.category.BlogCategory;
import com.very.blog.content.entity.tag.BlogTag;
import com.very.blog.content.enums.article.ArticleStatusEnum;
import com.very.blog.content.enums.common.DeletedStatusEnum;
import com.very.blog.content.enums.common.EnableStatusEnum;
import com.very.blog.content.exception.common.ContentBizException;
import com.very.blog.content.mapper.article.BlogArticleMapper;
import com.very.blog.content.mapper.article.BlogArticleTagMapper;
import com.very.blog.content.mapper.category.BlogCategoryMapper;
import com.very.blog.content.mapper.tag.BlogTagMapper;
import com.very.blog.content.service.article.ArticleSearchService;
import com.very.blog.content.service.article.ArticleService;
import com.very.blog.content.vo.article.ArticleVO;
import com.very.blog.content.vo.article.RpcArticleVO;
import com.very.blog.content.vo.tag.TagVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 文章服务实现
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    /**
     * 文章Mapper
     */
    private final BlogArticleMapper blogArticleMapper;

    /**
     * 文章分类Mapper
     */
    private final BlogCategoryMapper blogCategoryMapper;

    /**
     * 文章标签Mapper
     */
    private final BlogTagMapper blogTagMapper;

    /**
     * 文章标签关联Mapper
     */
    private final BlogArticleTagMapper blogArticleTagMapper;

    /**
     * 文章搜索服务
     */
    private final ArticleSearchService articleSearchService;

    /**
     * 构造文章服务实现
     *
     * @param blogArticleMapper 文章Mapper
     * @param blogCategoryMapper 文章分类Mapper
     * @param blogTagMapper 文章标签Mapper
     * @param blogArticleTagMapper 文章标签关联Mapper
     * @param articleSearchService 文章搜索服务
     */
    public ArticleServiceImpl(BlogArticleMapper blogArticleMapper,
                              BlogCategoryMapper blogCategoryMapper,
                              BlogTagMapper blogTagMapper,
                              BlogArticleTagMapper blogArticleTagMapper,
                              ArticleSearchService articleSearchService) {
        this.blogArticleMapper = blogArticleMapper;
        this.blogCategoryMapper = blogCategoryMapper;
        this.blogTagMapper = blogTagMapper;
        this.blogArticleTagMapper = blogArticleTagMapper;
        this.articleSearchService = articleSearchService;
    }

    /**
     * 创建文章
     *
     * @param dto 文章创建入参
     * @return 文章信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleVO create(ArticleCreateDTO dto) {
        BlogCategory category = getCategoryEntity(dto.getCategoryId());
        List<BlogTag> tags = getTagEntities(dto.getTagIds());
        BlogArticle entity = ArticleConvert.toEntity(dto);
        entity.setDeleted(DeletedStatusEnum.NOT_DELETED.getCode());
        if (ArticleStatusEnum.PUBLISHED.getCode().equals(entity.getArticleStatus())) {
            entity.setPublishTime(LocalDateTime.now());
        }
        blogArticleMapper.insert(entity);
        saveArticleTags(entity.getId(), tags);
        if (ArticleStatusEnum.PUBLISHED.getCode().equals(entity.getArticleStatus())) {
            articleSearchService.savePublishedArticle(entity, category, tags);
        }
        return toVO(entity, category, tags);
    }

    /**
     * 更新文章
     *
     * @param dto 文章更新入参
     * @return 文章信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleVO update(ArticleUpdateDTO dto) {
        BlogArticle oldArticle = getEnabledEntity(dto.getId());
        BlogCategory category = getCategoryEntity(dto.getCategoryId());
        List<BlogTag> tags = getTagEntities(dto.getTagIds());
        BlogArticle entity = ArticleConvert.toEntity(dto);
        entity.setArticleStatus(oldArticle.getArticleStatus());
        entity.setPublishTime(oldArticle.getPublishTime());
        blogArticleMapper.updateById(entity);
        saveArticleTags(dto.getId(), tags);
        BlogArticle newArticle = getEnabledEntity(dto.getId());
        if (ArticleStatusEnum.PUBLISHED.getCode().equals(newArticle.getArticleStatus())) {
            articleSearchService.savePublishedArticle(newArticle, category, tags);
        }
        return toVO(newArticle, category, tags);
    }

    /**
     * 删除文章
     *
     * @param dto 文章删除入参
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ArticleDeleteDTO dto) {
        BlogArticle entity = getEnabledEntity(dto.getId());
        entity.setDeleted(DeletedStatusEnum.DELETED.getCode());
        blogArticleMapper.updateById(entity);
        blogArticleTagMapper.delete(Wrappers.<BlogArticleTag>lambdaQuery()
                .eq(BlogArticleTag::getArticleId, dto.getId()));
        articleSearchService.deleteArticle(dto.getId());
    }

    /**
     * 分页查询文章
     *
     * @param dto 文章分页查询入参
     * @return 文章分页列表
     */
    @Override
    public PageResult<ArticleVO> page(ArticlePageDTO dto) {
        Page<BlogArticle> page = blogArticleMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()),
                Wrappers.<BlogArticle>lambdaQuery()
                        .eq(BlogArticle::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                        .eq(dto.getCategoryId() != null, BlogArticle::getCategoryId, dto.getCategoryId())
                        .eq(dto.getArticleStatus() != null, BlogArticle::getArticleStatus, dto.getArticleStatus())
                        .and(isNotBlank(dto.getKeyword()), wrapper -> wrapper
                                .like(BlogArticle::getTitle, dto.getKeyword())
                                .or()
                                .like(BlogArticle::getSummary, dto.getKeyword()))
                        .orderByAsc(BlogArticle::getSortOrder)
                        .orderByDesc(BlogArticle::getId));
        return PageResult.of(page.getTotal(), page.getRecords().stream()
                .map(article -> toVO(article, getCategoryEntity(article.getCategoryId()), getTagsByArticleId(article.getId())))
                .toList());
    }

    /**
     * 根据文章ID查询文章
     *
     * @param id 文章ID
     * @return 文章信息
     */
    @Override
    public ArticleVO getById(Long id) {
        BlogArticle article = getEnabledEntity(id);
        return toVO(article, getCategoryEntity(article.getCategoryId()), getTagsByArticleId(article.getId()));
    }

    /**
     * 发布文章
     *
     * @param dto 文章发布入参
     * @return 文章信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleVO publish(ArticlePublishDTO dto) {
        BlogArticle article = getEnabledEntity(dto.getId());
        article.setArticleStatus(ArticleStatusEnum.PUBLISHED.getCode());
        article.setPublishTime(article.getPublishTime() == null ? LocalDateTime.now() : article.getPublishTime());
        blogArticleMapper.updateById(article);
        BlogCategory category = getCategoryEntity(article.getCategoryId());
        List<BlogTag> tags = getTagsByArticleId(article.getId());
        articleSearchService.savePublishedArticle(article, category, tags);
        return toVO(article, category, tags);
    }

    /**
     * 下线文章
     *
     * @param dto 文章下线入参
     * @return 文章信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleVO offline(ArticleOfflineDTO dto) {
        BlogArticle article = getEnabledEntity(dto.getId());
        article.setArticleStatus(ArticleStatusEnum.OFFLINE.getCode());
        blogArticleMapper.updateById(article);
        articleSearchService.deleteArticle(article.getId());
        return toVO(article, getCategoryEntity(article.getCategoryId()), getTagsByArticleId(article.getId()));
    }

    /**
     * 查询RPC文章详情
     *
     * @param dto RPC文章查询入参
     * @return RPC文章信息
     */
    @Override
    public RpcArticleVO getRpcArticle(RpcArticleQueryDTO dto) {
        BlogArticle article = getRpcArticleEntity(dto.getId(), dto.getPublishedOnly());
        BlogCategory category = getRpcCategoryEntity(article.getCategoryId());
        return ArticleConvert.toRpcVO(article, category, getTagsByArticleId(article.getId()));
    }

    /**
     * 查询RPC文章列表
     *
     * @param dto RPC文章列表查询入参
     * @return RPC文章列表
     */
    @Override
    public List<RpcArticleVO> listRpcArticles(RpcArticleListDTO dto) {
        List<Long> articleIds = dto.getIds().stream().distinct().toList();
        List<BlogArticle> articles = blogArticleMapper.selectList(Wrappers.<BlogArticle>lambdaQuery()
                .in(BlogArticle::getId, articleIds)
                .eq(BlogArticle::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                .eq(Boolean.TRUE.equals(dto.getPublishedOnly()), BlogArticle::getArticleStatus, ArticleStatusEnum.PUBLISHED.getCode()));
        if (articles.isEmpty()) {
            return List.of();
        }
        Map<Long, BlogArticle> articleMap = articles.stream()
                .collect(Collectors.toMap(BlogArticle::getId, Function.identity()));
        Map<Long, BlogCategory> categoryMap = getRpcCategoryMap(articles);
        Map<Long, List<BlogTag>> tagMap = getRpcTagMap(articleIds);
        return articleIds.stream()
                .map(articleMap::get)
                .filter(article -> article != null)
                .map(article -> ArticleConvert.toRpcVO(article, categoryMap.get(article.getCategoryId()),
                        tagMap.getOrDefault(article.getId(), List.of())))
                .toList();
    }

    /**
     * 校验RPC文章是否存在
     *
     * @param dto RPC文章查询入参
     * @return 是否存在
     */
    @Override
    public Boolean existsRpcArticle(RpcArticleQueryDTO dto) {
        Long count = blogArticleMapper.selectCount(Wrappers.<BlogArticle>lambdaQuery()
                .eq(BlogArticle::getId, dto.getId())
                .eq(BlogArticle::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                .eq(Boolean.TRUE.equals(dto.getPublishedOnly()), BlogArticle::getArticleStatus, ArticleStatusEnum.PUBLISHED.getCode()));
        return count > 0;
    }

    /**
     * 保存文章标签关联
     *
     * @param articleId 文章ID
     * @param tags 标签列表
     */
    private void saveArticleTags(Long articleId, List<BlogTag> tags) {
        blogArticleTagMapper.delete(Wrappers.<BlogArticleTag>lambdaQuery()
                .eq(BlogArticleTag::getArticleId, articleId));
        tags.forEach(tag -> {
            BlogArticleTag articleTag = new BlogArticleTag();
            articleTag.setArticleId(articleId);
            articleTag.setTagId(tag.getId());
            blogArticleTagMapper.insert(articleTag);
        });
    }

    /**
     * 查询文章标签列表
     *
     * @param articleId 文章ID
     * @return 标签列表
     */
    private List<BlogTag> getTagsByArticleId(Long articleId) {
        List<Long> tagIds = blogArticleTagMapper.selectList(Wrappers.<BlogArticleTag>lambdaQuery()
                        .eq(BlogArticleTag::getArticleId, articleId))
                .stream()
                .map(BlogArticleTag::getTagId)
                .toList();
        return getTagEntities(tagIds);
    }

    /**
     * 查询标签实体列表
     *
     * @param tagIds 标签ID列表
     * @return 标签实体列表
     */
    private List<BlogTag> getTagEntities(List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> distinctTagIds = tagIds.stream().distinct().toList();
        List<BlogTag> tags = blogTagMapper.selectList(Wrappers.<BlogTag>lambdaQuery()
                .in(BlogTag::getId, distinctTagIds)
                .eq(BlogTag::getStatus, EnableStatusEnum.ENABLED.getCode())
                .eq(BlogTag::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (tags.size() != distinctTagIds.size()) {
            throw new ContentBizException("标签不存在或已禁用");
        }
        return tags;
    }

    /**
     * 查询分类实体
     *
     * @param categoryId 分类ID
     * @return 分类实体
     */
    private BlogCategory getCategoryEntity(Long categoryId) {
        BlogCategory category = blogCategoryMapper.selectOne(Wrappers.<BlogCategory>lambdaQuery()
                .eq(BlogCategory::getId, categoryId)
                .eq(BlogCategory::getStatus, EnableStatusEnum.ENABLED.getCode())
                .eq(BlogCategory::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (category == null) {
            throw new ContentBizException("分类不存在或已禁用");
        }
        return category;
    }

    /**
     * 查询未删除文章实体
     *
     * @param id 文章ID
     * @return 文章实体
     */
    private BlogArticle getEnabledEntity(Long id) {
        BlogArticle article = blogArticleMapper.selectOne(Wrappers.<BlogArticle>lambdaQuery()
                .eq(BlogArticle::getId, id)
                .eq(BlogArticle::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
        if (article == null) {
            throw new ContentBizException("文章不存在");
        }
        return article;
    }

    /**
     * 查询RPC文章实体
     *
     * @param id 文章ID
     * @param publishedOnly 是否只查询已发布文章
     * @return 文章实体
     */
    private BlogArticle getRpcArticleEntity(Long id, Boolean publishedOnly) {
        BlogArticle article = blogArticleMapper.selectOne(Wrappers.<BlogArticle>lambdaQuery()
                .eq(BlogArticle::getId, id)
                .eq(BlogArticle::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode())
                .eq(Boolean.TRUE.equals(publishedOnly), BlogArticle::getArticleStatus, ArticleStatusEnum.PUBLISHED.getCode()));
        if (article == null) {
            throw new ContentBizException("文章不存在");
        }
        return article;
    }

    /**
     * 查询RPC分类实体
     *
     * @param categoryId 分类ID
     * @return 分类实体
     */
    private BlogCategory getRpcCategoryEntity(Long categoryId) {
        return blogCategoryMapper.selectOne(Wrappers.<BlogCategory>lambdaQuery()
                .eq(BlogCategory::getId, categoryId)
                .eq(BlogCategory::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()));
    }

    /**
     * 查询RPC分类映射
     *
     * @param articles 文章列表
     * @return 分类映射
     */
    private Map<Long, BlogCategory> getRpcCategoryMap(List<BlogArticle> articles) {
        List<Long> categoryIds = articles.stream()
                .map(BlogArticle::getCategoryId)
                .distinct()
                .toList();
        return blogCategoryMapper.selectList(Wrappers.<BlogCategory>lambdaQuery()
                        .in(BlogCategory::getId, categoryIds)
                        .eq(BlogCategory::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()))
                .stream()
                .collect(Collectors.toMap(BlogCategory::getId, Function.identity()));
    }

    /**
     * 查询RPC文章标签映射
     *
     * @param articleIds 文章ID列表
     * @return 文章标签映射
     */
    private Map<Long, List<BlogTag>> getRpcTagMap(List<Long> articleIds) {
        List<BlogArticleTag> articleTags = blogArticleTagMapper.selectList(Wrappers.<BlogArticleTag>lambdaQuery()
                .in(BlogArticleTag::getArticleId, articleIds));
        if (articleTags.isEmpty()) {
            return Map.of();
        }
        List<Long> tagIds = articleTags.stream()
                .map(BlogArticleTag::getTagId)
                .distinct()
                .toList();
        Map<Long, BlogTag> tagMap = blogTagMapper.selectList(Wrappers.<BlogTag>lambdaQuery()
                        .in(BlogTag::getId, tagIds)
                        .eq(BlogTag::getStatus, EnableStatusEnum.ENABLED.getCode())
                        .eq(BlogTag::getDeleted, DeletedStatusEnum.NOT_DELETED.getCode()))
                .stream()
                .collect(Collectors.toMap(BlogTag::getId, Function.identity()));
        return articleTags.stream()
                .filter(articleTag -> tagMap.containsKey(articleTag.getTagId()))
                .collect(Collectors.groupingBy(BlogArticleTag::getArticleId,
                        Collectors.mapping(articleTag -> tagMap.get(articleTag.getTagId()), Collectors.toList())));
    }

    /**
     * 转换文章出参
     *
     * @param article 文章实体
     * @param category 分类实体
     * @param tags 标签列表
     * @return 文章出参
     */
    private ArticleVO toVO(BlogArticle article, BlogCategory category, List<BlogTag> tags) {
        List<TagVO> tagVos = tags.stream()
                .map(TagConvert::toVO)
                .toList();
        return ArticleConvert.toVO(article, category, tagVos);
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
