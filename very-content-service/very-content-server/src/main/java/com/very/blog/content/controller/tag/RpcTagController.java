package com.very.blog.content.controller.tag;

import com.very.blog.common.core.result.Result;
import com.very.blog.content.dto.tag.RpcTagListDTO;
import com.very.blog.content.dto.tag.RpcTagQueryDTO;
import com.very.blog.content.feign.tag.RpcTagFeignClient;
import com.very.blog.content.service.tag.TagService;
import com.very.blog.content.vo.tag.RpcTagVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RPC标签控制器
 */
@RestController
public class RpcTagController implements RpcTagFeignClient {

    /**
     * 标签服务
     */
    private final TagService tagService;

    /**
     * 构造RPC标签控制器
     *
     * @param tagService 标签服务
     */
    public RpcTagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * 查询RPC标签详情
     *
     * @param dto RPC标签查询入参
     * @return RPC标签信息
     */
    @Override
    public Result<RpcTagVO> getTag(@Valid @RequestBody RpcTagQueryDTO dto) {
        return Result.success(tagService.getRpcTag(dto));
    }

    /**
     * 查询RPC标签列表
     *
     * @param dto RPC标签列表查询入参
     * @return RPC标签列表
     */
    @Override
    public Result<List<RpcTagVO>> listTags(@Valid @RequestBody RpcTagListDTO dto) {
        return Result.success(tagService.listRpcTags(dto));
    }
}
