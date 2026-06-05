package com.very.blog.content.controller.article;

import com.very.blog.common.core.result.Result;
import com.very.blog.content.dto.article.RpcArticleListDTO;
import com.very.blog.content.dto.article.RpcArticleQueryDTO;
import com.very.blog.content.feign.article.RpcArticleFeignClient;
import com.very.blog.content.service.article.ArticleService;
import com.very.blog.content.vo.article.RpcArticleVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RPC文章控制器
 */
@RestController
public class RpcArticleController implements RpcArticleFeignClient {

    /**
     * 文章服务
     */
    private final ArticleService articleService;

    /**
     * 构造RPC文章控制器
     *
     * @param articleService 文章服务
     */
    public RpcArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 查询RPC文章详情
     *
     * @param dto RPC文章查询入参
     * @return RPC文章信息
     */
    @Override
    public Result<RpcArticleVO> getArticle(@Valid @RequestBody RpcArticleQueryDTO dto) {
        return Result.success(articleService.getRpcArticle(dto));
    }

    /**
     * 查询RPC文章列表
     *
     * @param dto RPC文章列表查询入参
     * @return RPC文章列表
     */
    @Override
    public Result<List<RpcArticleVO>> listArticles(@Valid @RequestBody RpcArticleListDTO dto) {
        return Result.success(articleService.listRpcArticles(dto));
    }

    /**
     * 校验RPC文章是否存在
     *
     * @param dto RPC文章查询入参
     * @return 是否存在
     */
    @Override
    public Result<Boolean> existsArticle(@Valid @RequestBody RpcArticleQueryDTO dto) {
        return Result.success(articleService.existsRpcArticle(dto));
    }
}
