package com.very.blog.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.very.blog.user.entity.SysOperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台操作日志Mapper
 */
@Mapper
public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {
}
