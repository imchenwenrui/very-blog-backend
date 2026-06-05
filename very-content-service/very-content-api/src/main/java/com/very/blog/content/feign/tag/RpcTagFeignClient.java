package com.very.blog.content.feign.tag;

import com.very.blog.common.core.result.Result;
import com.very.blog.content.constant.ContentServiceConstants;
import com.very.blog.content.dto.tag.RpcTagListDTO;
import com.very.blog.content.dto.tag.RpcTagQueryDTO;
import com.very.blog.content.vo.tag.RpcTagVO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * RPC标签Feign客户端
 */
@FeignClient(contextId = "rpcTagFeignClient", value = ContentServiceConstants.CONTENT_SERVICE_NAME)
public interface RpcTagFeignClient {

    /**
     * 查询RPC标签详情
     *
     * @param dto RPC标签查询入参
     * @return RPC标签信息
     */
    @PostMapping(ContentServiceConstants.RPC_TAG_DETAIL)
    Result<RpcTagVO> getTag(@Valid @RequestBody RpcTagQueryDTO dto);

    /**
     * 查询RPC标签列表
     *
     * @param dto RPC标签列表查询入参
     * @return RPC标签列表
     */
    @PostMapping(ContentServiceConstants.RPC_TAG_LIST)
    Result<List<RpcTagVO>> listTags(@Valid @RequestBody RpcTagListDTO dto);
}
