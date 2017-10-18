package com.huang.centralconf.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huang.centralconf.manager.entry.po.Resource;

import tk.mybatis.mapper.common.BaseMapper;

@SuppressWarnings("ALL")
public interface ResourceMapper extends BaseMapper<Resource> {

	List<Resource> selectAllByUserId(@Param("userId") Long userId, @Param("type") Integer type);

}
