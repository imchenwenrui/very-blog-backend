package com.very.blog.content.feign.category;

import com.very.blog.common.core.result.Result;
import com.very.blog.content.constant.ContentServiceConstants;
import com.very.blog.content.dto.category.RpcCategoryListDTO;
import com.very.blog.content.dto.category.RpcCategoryQueryDTO;
import com.very.blog.content.vo.category.RpcCategoryVO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * RPC分类Feign客户端
 */
@FeignClient(contextId = "rpcCategoryFeignClient", value = ContentServiceConstants.CONTENT_SERVICE_NAME)
public interface RpcCategoryFeignClient {

    /**
     * 查询RPC分类详情
     *
     * @param dto RPC分类查询入参
     * @return RPC分类信息
     */
    @PostMapping(ContentServiceConstants.RPC_CATEGORY_DETAIL)
    Result<RpcCategoryVO> getCategory(@Valid @RequestBody RpcCategoryQueryDTO dto);

    /**
     * 查询RPC分类列表
     *
     * @param dto RPC分类列表查询入参
     * @return RPC分类列表
     */
    @PostMapping(ContentServiceConstants.RPC_CATEGORY_LIST)
    Result<List<RpcCategoryVO>> listCategories(@Valid @RequestBody RpcCategoryListDTO dto);
}
