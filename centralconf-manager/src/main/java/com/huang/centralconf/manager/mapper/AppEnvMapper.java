package com.huang.centralconf.manager.mapper;

import com.huang.centralconf.manager.entry.po.AppEnv;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface AppEnvMapper extends BaseMapper<AppEnv> {

    public List<AppEnv> geEnv(@Param("appId") Long appId, @Param("UserId") Long UserId, @Param("type") Integer type, @Param("common") String common);

}
