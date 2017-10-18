package com.huang.centralconf.manager.mapper;

import com.huang.centralconf.manager.entry.po.Item;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@SuppressWarnings("ALL")
public interface ItemMapper extends BaseMapper<Item> {

    List<Item> getRelationResourceItem(@Param("appId") Long appId, @Param("envId") Long envId);

    List<Item> getBindItem(@Param("appId") Long appId, @Param("envId") Long envId, @Param("resId") Long resId, @Param("resEnvId") Long resEnvId);

}
