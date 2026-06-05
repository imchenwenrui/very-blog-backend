package com.very.blog.content.feign.article;

import com.very.blog.common.core.result.Result;
import com.very.blog.content.constant.ContentServiceConstants;
import com.very.blog.content.dto.article.RpcArticleListDTO;
import com.very.blog.content.dto.article.RpcArticleQueryDTO;
import com.very.blog.content.vo.article.RpcArticleVO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * RPC文章Feign客户端
 */
@FeignClient(contextId = "rpcArticleFeignClient", value = ContentServiceConstants.CONTENT_SERVICE_NAME)
public interface RpcArticleFeignClient {

    /**
     * 查询RPC文章详情
     *
     * @param dto RPC文章查询入参
     * @return RPC文章信息
     */
    @PostMapping(ContentServiceConstants.RPC_ARTICLE_DETAIL)
    Result<RpcArticleVO> getArticle(@Valid @RequestBody RpcArticleQueryDTO dto);

    /**
     * 查询RPC文章列表
     *
     * @param dto RPC文章列表查询入参
     * @return RPC文章列表
     */
    @PostMapping(ContentServiceConstants.RPC_ARTICLE_LIST)
    Result<List<RpcArticleVO>> listArticles(@Valid @RequestBody RpcArticleListDTO dto);

    /**
     * 校验RPC文章是否存在
     *
     * @param dto RPC文章查询入参
     * @return 是否存在
     */
    @PostMapping(ContentServiceConstants.RPC_ARTICLE_EXISTS)
    Result<Boolean> existsArticle(@Valid @RequestBody RpcArticleQueryDTO dto);
}
