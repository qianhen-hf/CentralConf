package com.huang.centralconf.configService.mapper;

import java.util.List;

import com.huang.centralconf.configService.entry.po.Item;
import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.BaseMapper;

@SuppressWarnings("ALL")
public interface ItemMapper extends BaseMapper<Item> {

	List<Item> getRelationResourceItem(@Param("appId") Long appId, @Param("envId") Long envId);

	List<Item> getBindItem(@Param("appId") Long appId,@Param("envId") Long envId,@Param("resId") Long resId,@Param("resEnvId") Long resEnvId);

}
