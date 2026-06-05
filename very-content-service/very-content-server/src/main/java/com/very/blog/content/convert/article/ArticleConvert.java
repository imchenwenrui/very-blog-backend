package com.very.blog.content.convert.article;

import com.very.blog.content.dto.article.ArticleCreateDTO;
import com.very.blog.content.dto.article.ArticleUpdateDTO;
import com.very.blog.content.entity.article.BlogArticle;
import com.very.blog.content.entity.category.BlogCategory;
import com.very.blog.content.entity.tag.BlogTag;
import com.very.blog.content.vo.article.ArticleVO;
import com.very.blog.content.vo.article.RpcArticleVO;
import com.very.blog.content.vo.tag.TagVO;

import java.util.List;

/**
 * 文章对象转换工具
 */
public final class ArticleConvert {

    /**
     * 工具类构造方法
     */
    private ArticleConvert() {
    }

    /**
     * 将文章创建入参转换为文章实体
     *
     * @param dto 文章创建入参
     * @return 文章实体
     */
    public static BlogArticle toEntity(ArticleCreateDTO dto) {
        BlogArticle entity = new BlogArticle();
        entity.setCategoryId(dto.getCategoryId());
        entity.setTitle(dto.getTitle());
        entity.setSlug(dto.getSlug());
        entity.setSummary(dto.getSummary());
        entity.setCover(dto.getCover());
        entity.setContent(dto.getContent());
        entity.setContentFormat(dto.getContentFormat());
        entity.setArticleStatus(dto.getArticleStatus());
        entity.setSortOrder(dto.getSortOrder());
        return entity;
    }

    /**
     * 将文章更新入参转换为文章实体
     *
     * @param dto 文章更新入参
     * @return 文章实体
     */
    public static BlogArticle toEntity(ArticleUpdateDTO dto) {
        BlogArticle entity = new BlogArticle();
        entity.setId(dto.getId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setTitle(dto.getTitle());
        entity.setSlug(dto.getSlug());
        entity.setSummary(dto.getSummary());
        entity.setCover(dto.getCover());
        entity.setContent(dto.getContent());
        entity.setContentFormat(dto.getContentFormat());
        entity.setSortOrder(dto.getSortOrder());
        return entity;
    }

    /**
     * 将文章实体转换为文章出参
     *
     * @param entity 文章实体
     * @param category 分类实体
     * @param tags 标签列表
     * @return 文章出参
     */
    public static ArticleVO toVO(BlogArticle entity, BlogCategory category, List<TagVO> tags) {
        if (entity == null) {
            return null;
        }
        ArticleVO vo = new ArticleVO();
        vo.setId(entity.getId());
        vo.setCategoryId(entity.getCategoryId());
        vo.setCategoryName(category == null ? null : category.getCategoryName());
        vo.setTitle(entity.getTitle());
        vo.setSlug(entity.getSlug());
        vo.setSummary(entity.getSummary());
        vo.setCover(entity.getCover());
        vo.setContent(entity.getContent());
        vo.setContentFormat(entity.getContentFormat());
        vo.setArticleStatus(entity.getArticleStatus());
        vo.setPublishTime(entity.getPublishTime());
        vo.setSortOrder(entity.getSortOrder());
        vo.setTags(tags);
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    /**
     * 将文章实体转换为RPC文章出参
     *
     * @param entity 文章实体
     * @param category 分类实体
     * @param tags 标签列表
     * @return RPC文章出参
     */
    public static RpcArticleVO toRpcVO(BlogArticle entity, BlogCategory category, List<BlogTag> tags) {
        if (entity == null) {
            return null;
        }
        RpcArticleVO vo = new RpcArticleVO();
        vo.setId(entity.getId());
        vo.setCategoryId(entity.getCategoryId());
        vo.setCategoryName(category == null ? null : category.getCategoryName());
        vo.setTitle(entity.getTitle());
        vo.setSlug(entity.getSlug());
        vo.setSummary(entity.getSummary());
        vo.setCover(entity.getCover());
        vo.setArticleStatus(entity.getArticleStatus());
        vo.setPublishTime(entity.getPublishTime());
        vo.setTagIds(tags == null ? List.of() : tags.stream().map(BlogTag::getId).toList());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }
}
