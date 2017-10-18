package com.huang.centralconf.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huang.centralconf.manager.entry.po.BindItem;

import tk.mybatis.mapper.common.BaseMapper;

@SuppressWarnings("ALL")
public interface BindItemMapper extends BaseMapper<BindItem> {

	List<BindItem> getBindItem(@Param("appId") Long appId,@Param("envId") Long envId);

}
