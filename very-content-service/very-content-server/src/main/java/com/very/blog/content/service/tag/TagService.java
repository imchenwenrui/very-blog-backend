package com.very.blog.content.service.tag;

import com.very.blog.common.core.result.PageResult;
import com.very.blog.content.dto.tag.TagCreateDTO;
import com.very.blog.content.dto.tag.TagDeleteDTO;
import com.very.blog.content.dto.tag.TagPageDTO;
import com.very.blog.content.dto.tag.TagUpdateDTO;
import com.very.blog.content.dto.tag.RpcTagListDTO;
import com.very.blog.content.dto.tag.RpcTagQueryDTO;
import com.very.blog.content.vo.tag.RpcTagVO;
import com.very.blog.content.vo.tag.TagVO;

import java.util.List;

/**
 * 标签服务
 */
public interface TagService {

    /**
     * 创建标签
     *
     * @param dto 标签创建入参
     * @return 标签信息
     */
    TagVO create(TagCreateDTO dto);

    /**
     * 更新标签
     *
     * @param dto 标签更新入参
     * @return 标签信息
     */
    TagVO update(TagUpdateDTO dto);

    /**
     * 删除标签
     *
     * @param dto 标签删除入参
     */
    void delete(TagDeleteDTO dto);

    /**
     * 分页查询标签
     *
     * @param dto 标签分页查询入参
     * @return 标签分页列表
     */
    PageResult<TagVO> page(TagPageDTO dto);

    /**
     * 根据标签ID查询标签
     *
     * @param id 标签ID
     * @return 标签信息
     */
    TagVO getById(Long id);

    /**
     * 查询标签列表
     *
     * @return 标签列表
     */
    List<TagVO> list();

    /**
     * 查询RPC标签详情
     *
     * @param dto RPC标签查询入参
     * @return RPC标签信息
     */
    RpcTagVO getRpcTag(RpcTagQueryDTO dto);

    /**
     * 查询RPC标签列表
     *
     * @param dto RPC标签列表查询入参
     * @return RPC标签列表
     */
    List<RpcTagVO> listRpcTags(RpcTagListDTO dto);
}
