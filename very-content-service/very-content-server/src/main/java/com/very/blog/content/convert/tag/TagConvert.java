package com.very.blog.content.convert.tag;

import com.very.blog.content.dto.tag.TagCreateDTO;
import com.very.blog.content.dto.tag.TagUpdateDTO;
import com.very.blog.content.entity.tag.BlogTag;
import com.very.blog.content.vo.tag.RpcTagVO;
import com.very.blog.content.vo.tag.TagVO;

/**
 * 标签对象转换工具
 */
public final class TagConvert {

    /**
     * 工具类构造方法
     */
    private TagConvert() {
    }

    /**
     * 将标签创建入参转换为标签实体
     *
     * @param dto 标签创建入参
     * @return 标签实体
     */
    public static BlogTag toEntity(TagCreateDTO dto) {
        BlogTag entity = new BlogTag();
        entity.setTagCode(dto.getTagCode());
        entity.setTagName(dto.getTagName());
        entity.setDescription(dto.getDescription());
        entity.setSortOrder(dto.getSortOrder());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    /**
     * 将标签更新入参转换为标签实体
     *
     * @param dto 标签更新入参
     * @return 标签实体
     */
    public static BlogTag toEntity(TagUpdateDTO dto) {
        BlogTag entity = new BlogTag();
        entity.setId(dto.getId());
        entity.setTagCode(dto.getTagCode());
        entity.setTagName(dto.getTagName());
        entity.setDescription(dto.getDescription());
        entity.setSortOrder(dto.getSortOrder());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    /**
     * 将标签实体转换为标签出参
     *
     * @param entity 标签实体
     * @return 标签出参
     */
    public static TagVO toVO(BlogTag entity) {
        if (entity == null) {
            return null;
        }
        TagVO vo = new TagVO();
        vo.setId(entity.getId());
        vo.setTagCode(entity.getTagCode());
        vo.setTagName(entity.getTagName());
        vo.setDescription(entity.getDescription());
        vo.setSortOrder(entity.getSortOrder());
        vo.setStatus(entity.getStatus());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    /**
     * 将标签实体转换为RPC标签出参
     *
     * @param entity 标签实体
     * @return RPC标签出参
     */
    public static RpcTagVO toRpcVO(BlogTag entity) {
        if (entity == null) {
            return null;
        }
        RpcTagVO vo = new RpcTagVO();
        vo.setId(entity.getId());
        vo.setTagCode(entity.getTagCode());
        vo.setTagName(entity.getTagName());
        vo.setStatus(entity.getStatus());
        return vo;
    }
}
