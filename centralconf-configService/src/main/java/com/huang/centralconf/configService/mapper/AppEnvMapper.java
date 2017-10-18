package com.huang.centralconf.configService.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huang.centralconf.configService.entry.po.AppEnv;

import tk.mybatis.mapper.common.BaseMapper;

@SuppressWarnings("ALL")
public interface AppEnvMapper extends BaseMapper<AppEnv> {

	public List<AppEnv> geEnv(@Param("appId") Long appId, @Param("UserId") Long UserId, @Param("type") Integer type, @Param("common") String common);

}
