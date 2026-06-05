package com.very.blog.content.service.article;

import com.very.blog.common.core.result.PageResult;
import com.very.blog.content.dto.article.ArticleCreateDTO;
import com.very.blog.content.dto.article.ArticleDeleteDTO;
import com.very.blog.content.dto.article.ArticleOfflineDTO;
import com.very.blog.content.dto.article.ArticlePageDTO;
import com.very.blog.content.dto.article.ArticlePublishDTO;
import com.very.blog.content.dto.article.ArticleUpdateDTO;
import com.very.blog.content.dto.article.RpcArticleListDTO;
import com.very.blog.content.dto.article.RpcArticleQueryDTO;
import com.very.blog.content.vo.article.ArticleVO;
import com.very.blog.content.vo.article.RpcArticleVO;

import java.util.List;

/**
 * 文章服务
 */
public interface ArticleService {

    /**
     * 创建文章
     *
     * @param dto 文章创建入参
     * @return 文章信息
     */
    ArticleVO create(ArticleCreateDTO dto);

    /**
     * 更新文章
     *
     * @param dto 文章更新入参
     * @return 文章信息
     */
    ArticleVO update(ArticleUpdateDTO dto);

    /**
     * 删除文章
     *
     * @param dto 文章删除入参
     */
    void delete(ArticleDeleteDTO dto);

    /**
     * 分页查询文章
     *
     * @param dto 文章分页查询入参
     * @return 文章分页列表
     */
    PageResult<ArticleVO> page(ArticlePageDTO dto);

    /**
     * 根据文章ID查询文章
     *
     * @param id 文章ID
     * @return 文章信息
     */
    ArticleVO getById(Long id);

    /**
     * 发布文章
     *
     * @param dto 文章发布入参
     * @return 文章信息
     */
    ArticleVO publish(ArticlePublishDTO dto);

    /**
     * 下线文章
     *
     * @param dto 文章下线入参
     * @return 文章信息
     */
    ArticleVO offline(ArticleOfflineDTO dto);

    /**
     * 查询RPC文章详情
     *
     * @param dto RPC文章查询入参
     * @return RPC文章信息
     */
    RpcArticleVO getRpcArticle(RpcArticleQueryDTO dto);

    /**
     * 查询RPC文章列表
     *
     * @param dto RPC文章列表查询入参
     * @return RPC文章列表
     */
    List<RpcArticleVO> listRpcArticles(RpcArticleListDTO dto);

    /**
     * 校验RPC文章是否存在
     *
     * @param dto RPC文章查询入参
     * @return 是否存在
     */
    Boolean existsRpcArticle(RpcArticleQueryDTO dto);
}
