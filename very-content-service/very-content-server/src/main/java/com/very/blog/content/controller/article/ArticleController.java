package com.very.blog.content.controller.article;

import com.very.blog.common.core.result.PageResult;
import com.very.blog.common.core.result.Result;
import com.very.blog.common.web.annotation.OperationLog;
import com.very.blog.common.web.enums.OperationContentEnum;
import com.very.blog.common.web.enums.OperationModuleEnum;
import com.very.blog.content.dto.article.ArticleCreateDTO;
import com.very.blog.content.dto.article.ArticleDeleteDTO;
import com.very.blog.content.dto.article.ArticleOfflineDTO;
import com.very.blog.content.dto.article.ArticlePageDTO;
import com.very.blog.content.dto.article.ArticlePublishDTO;
import com.very.blog.content.dto.article.ArticleUpdateDTO;
import com.very.blog.content.service.article.ArticleService;
import com.very.blog.content.vo.article.ArticleVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章管理控制器
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    /**
     * 文章服务
     */
    private final ArticleService articleService;

    /**
     * 构造文章管理控制器
     *
     * @param articleService 文章服务
     */
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 创建文章
     *
     * @param dto 文章创建入参
     * @return 文章信息
     */
    @OperationLog(module = OperationModuleEnum.ARTICLE, content = OperationContentEnum.CREATE)
    @PostMapping("/create")
    public Result<ArticleVO> create(@Valid @RequestBody ArticleCreateDTO dto) {
        return Result.success(articleService.create(dto));
    }

    /**
     * 更新文章
     *
     * @param dto 文章更新入参
     * @return 文章信息
     */
    @OperationLog(module = OperationModuleEnum.ARTICLE, content = OperationContentEnum.UPDATE)
    @PostMapping("/update")
    public Result<ArticleVO> update(@Valid @RequestBody ArticleUpdateDTO dto) {
        return Result.success(articleService.update(dto));
    }

    /**
     * 删除文章
     *
     * @param dto 文章删除入参
     * @return 删除结果
     */
    @OperationLog(module = OperationModuleEnum.ARTICLE, content = OperationContentEnum.DELETE)
    @PostMapping("/delete")
    public Result<Void> delete(@Valid @RequestBody ArticleDeleteDTO dto) {
        articleService.delete(dto);
        return Result.success();
    }

    /**
     * 分页查询文章
     *
     * @param dto 文章分页查询入参
     * @return 文章分页列表
     */
    @PostMapping("/page")
    public Result<PageResult<ArticleVO>> page(@Valid @RequestBody ArticlePageDTO dto) {
        return Result.success(articleService.page(dto));
    }

    /**
     * 根据文章ID查询文章
     *
     * @param id 文章ID
     * @return 文章信息
     */
    @GetMapping("/{id}")
    public Result<ArticleVO> getById(@PathVariable Long id) {
        return Result.success(articleService.getById(id));
    }

    /**
     * 发布文章
     *
     * @param dto 文章发布入参
     * @return 文章信息
     */
    @OperationLog(module = OperationModuleEnum.ARTICLE, content = OperationContentEnum.PUBLISH)
    @PostMapping("/publish")
    public Result<ArticleVO> publish(@Valid @RequestBody ArticlePublishDTO dto) {
        return Result.success(articleService.publish(dto));
    }

    /**
     * 下线文章
     *
     * @param dto 文章下线入参
     * @return 文章信息
     */
    @OperationLog(module = OperationModuleEnum.ARTICLE, content = OperationContentEnum.OFFLINE)
    @PostMapping("/offline")
    public Result<ArticleVO> offline(@Valid @RequestBody ArticleOfflineDTO dto) {
        return Result.success(articleService.offline(dto));
    }
}
