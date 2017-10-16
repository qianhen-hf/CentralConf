package com.huang.centralconf.configService.mapper;

import java.util.List;

import com.huang.centralconf.configService.entry.po.App;
import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.BaseMapper;

public interface AppMapper extends BaseMapper<App> {

	public List<App> selectAllByUserId(@Param("userId") Long userId, @Param("type") Integer type);
}
