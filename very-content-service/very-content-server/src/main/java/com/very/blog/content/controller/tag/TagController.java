package com.very.blog.content.controller.tag;

import com.very.blog.common.core.result.PageResult;
import com.very.blog.common.core.result.Result;
import com.very.blog.common.web.annotation.OperationLog;
import com.very.blog.common.web.enums.OperationContentEnum;
import com.very.blog.common.web.enums.OperationModuleEnum;
import com.very.blog.content.dto.tag.TagCreateDTO;
import com.very.blog.content.dto.tag.TagDeleteDTO;
import com.very.blog.content.dto.tag.TagPageDTO;
import com.very.blog.content.dto.tag.TagUpdateDTO;
import com.very.blog.content.service.tag.TagService;
import com.very.blog.content.vo.tag.TagVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标签管理控制器
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    /**
     * 标签服务
     */
    private final TagService tagService;

    /**
     * 构造标签管理控制器
     *
     * @param tagService 标签服务
     */
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * 创建标签
     *
     * @param dto 标签创建入参
     * @return 标签信息
     */
    @OperationLog(module = OperationModuleEnum.TAG, content = OperationContentEnum.CREATE)
    @PostMapping("/create")
    public Result<TagVO> create(@Valid @RequestBody TagCreateDTO dto) {
        return Result.success(tagService.create(dto));
    }

    /**
     * 更新标签
     *
     * @param dto 标签更新入参
     * @return 标签信息
     */
    @OperationLog(module = OperationModuleEnum.TAG, content = OperationContentEnum.UPDATE)
    @PostMapping("/update")
    public Result<TagVO> update(@Valid @RequestBody TagUpdateDTO dto) {
        return Result.success(tagService.update(dto));
    }

    /**
     * 删除标签
     *
     * @param dto 标签删除入参
     * @return 删除结果
     */
    @OperationLog(module = OperationModuleEnum.TAG, content = OperationContentEnum.DELETE)
    @PostMapping("/delete")
    public Result<Void> delete(@Valid @RequestBody TagDeleteDTO dto) {
        tagService.delete(dto);
        return Result.success();
    }

    /**
     * 分页查询标签
     *
     * @param dto 标签分页查询入参
     * @return 标签分页列表
     */
    @PostMapping("/page")
    public Result<PageResult<TagVO>> page(@Valid @RequestBody TagPageDTO dto) {
        return Result.success(tagService.page(dto));
    }

    /**
     * 根据标签ID查询标签
     *
     * @param id 标签ID
     * @return 标签信息
     */
    @GetMapping("/{id}")
    public Result<TagVO> getById(@PathVariable Long id) {
        return Result.success(tagService.getById(id));
    }

    /**
     * 查询标签列表
     *
     * @return 标签列表
     */
    @GetMapping("/list")
    public Result<List<TagVO>> list() {
        return Result.success(tagService.list());
    }
}
