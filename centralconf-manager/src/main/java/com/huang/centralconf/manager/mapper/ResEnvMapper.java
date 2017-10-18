package com.huang.centralconf.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huang.centralconf.manager.entry.po.ResEnv;

import tk.mybatis.mapper.common.BaseMapper;

@SuppressWarnings("ALL")
public interface ResEnvMapper extends BaseMapper<ResEnv> {

	public List<ResEnv> geEnv(@Param("resId") Long resId, @Param("UserId") Long UserId, @Param("type") Integer type);

}
