package com.huang.centralconf.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huang.centralconf.manager.entry.po.App;

import tk.mybatis.mapper.common.BaseMapper;

@SuppressWarnings("ALL")
public interface AppMapper extends BaseMapper<App> {

    public List<App> selectAllByUserId(@Param("userId") Long userId, @Param("type") Integer type);
}
