package com.huang.centralconf.manager.service;

import java.util.List;
import java.util.Map;

import com.huang.centralconf.manager.entry.vo.ItemVo;
import com.huang.centralconf.manager.entry.vo.JsonPage;
import com.huang.centralconf.manager.entry.vo.MyPage;
import com.huang.centralconf.manager.entry.vo.ResourceVo;

public interface ResourceService {

	void addRes(ResourceVo resourceVo);

	JsonPage<ResourceVo> getAllRes(MyPage myPage, Long userId, Integer type);

	void delRes(Long id);

	ResourceVo getResourceByName(String resName);

	ResourceVo getResourceById(Long resId);

	List<ResourceVo> getAllRes();

	JsonPage<ResourceVo> getAllResourceAndEnv(Long appId,Long envId);

	void addRelativeResource(Long appId, Long envId, Long resId, Long resEnvId, String itemValue);

	Map<String, Map<String, Object>> getRelativeResource(Long appId, Long envId);

	public JsonPage<ItemVo> getBindItem(Long appId, Long envId, Long resId, Long resEnvId);

	public Map<String, Map<String, Object>> getBindItem(Long appId, Long envId);

}
