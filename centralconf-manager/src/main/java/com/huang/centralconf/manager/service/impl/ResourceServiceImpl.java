package com.huang.centralconf.manager.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huang.centralconf.core.common.YtException;
import com.huang.centralconf.manager.entry.po.AppEnvResEnv;
import com.huang.centralconf.manager.entry.po.BindItem;
import com.huang.centralconf.manager.entry.po.Item;
import com.huang.centralconf.manager.entry.po.Resource;
import com.huang.centralconf.manager.entry.vo.ItemVo;
import com.huang.centralconf.manager.entry.vo.JsonPage;
import com.huang.centralconf.manager.entry.vo.MyPage;
import com.huang.centralconf.manager.entry.vo.ResEnvVo;
import com.huang.centralconf.manager.entry.vo.ResourceVo;
import com.huang.centralconf.manager.exception.YtfmUserErrors;
import com.huang.centralconf.manager.mapper.AppEnvResEnvMapper;
import com.huang.centralconf.manager.mapper.BindItemMapper;
import com.huang.centralconf.manager.mapper.ResourceMapper;
import com.huang.centralconf.manager.service.ItemService;
import com.huang.centralconf.manager.service.ResourceEnvService;
import com.huang.centralconf.manager.service.ResourceService;
import com.huang.centralconf.manager.util.ConvertUtil;
import com.huang.centralconf.manager.util.IdGen;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	ResourceEnvService resourceEnvService;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	AppEnvResEnvMapper appEnvResEnvMapper;
	@Autowired
	BindItemMapper bindItemMapper;
	@Autowired
	ItemService itemService;

	public void addRes(ResourceVo resourceVo) {
		Resource resource = new Resource();
		try {
			BeanUtils.copyProperties(resource, resourceVo);
		} catch (Exception e) {
			throw new YtException(YtfmUserErrors.COPY_PROPRER);
		}
		resource.setId(IdGen.get().nextId());
		resource.setCreateTime(System.currentTimeMillis());
		resourceMapper.insert(resource);
	}

	public JsonPage<ResourceVo> getAllRes(MyPage myPage, Long userId, Integer type) {
		Page<ResourceVo> page = PageHelper.startPage(myPage.getPage(), myPage.getResults(), true);
		List<Resource> permList = resourceMapper.selectAllByUserId(userId, type);
		List<ResourceVo> result = new ArrayList<ResourceVo>();
		JsonPage<ResourceVo> resultList = new JsonPage<ResourceVo>(page);
		for (Resource resource : permList) {
			ResourceVo resourceVo = new ResourceVo();
			try {
				BeanUtils.copyProperties(resourceVo, resource);
			} catch (Exception e) {
				throw new YtException(YtfmUserErrors.COPY_PROPRER);
			}
			result.add(resourceVo);
		}
		resultList.setRows(result);
		return resultList;
	}

	public void delRes(Long id) {
		resourceMapper.deleteByPrimaryKey(id);
	}

	public ResourceVo getResourceByName(String resName) {
		Resource condition = new Resource();
		condition.setResName(resName);
		Resource resource = resourceMapper.selectOne(condition);
		if (resource == null) {
			return null;
		}
		ResourceVo resourceVo = new ResourceVo();
		try {
			BeanUtils.copyProperties(resourceVo, resource);
		} catch (Exception e) {
			e.printStackTrace();
			throw new YtException(YtfmUserErrors.COPY_PROPRER);
		}
		return resourceVo;
	}

	public ResourceVo getResourceById(Long resId) {
		Resource resource = resourceMapper.selectByPrimaryKey(resId);
		ResourceVo resourceVo = new ResourceVo();
		try {
			BeanUtils.copyProperties(resourceVo, resource);
		} catch (Exception e) {
			throw new YtException(YtfmUserErrors.COPY_PROPRER);
		}
		return resourceVo;
	}

	public List<ResourceVo> getAllRes() {
		List<Resource> selectAll = resourceMapper.selectAll();
		List<ResourceVo> copyTo = ConvertUtil.copyTo(selectAll, ResourceVo.class);
		return copyTo;
	}

	public JsonPage<ResourceVo> getAllResourceAndEnv(Long appId, Long envId) {
		// Map<String, Object> result = new HashMap<String, Object>();
		JsonPage<ResourceVo> result = new JsonPage<ResourceVo>();
		Map<Long, Long> relation = this.getRelativeResourceSure(appId, envId);
		List<ResourceVo> allRes = this.getAllRes();
		for (ResourceVo resourceVo : allRes) {
			List<ResEnvVo> envs = resourceEnvService.getAllEnvByResId(resourceVo.getId());
			Long resEnvId = relation.get(resourceVo.getId());
			if (resEnvId != null) {
				for (ResEnvVo r : envs) {
					if (r.getId().longValue() == resEnvId.longValue()) {
						r.setSelected(true);
					}
				}
			}
			resourceVo.setEnvs(envs);
		}
		result.setRows(allRes);
		result.setPage(1);
		result.setTotal(new Long(allRes.size()));
		return result;
	}

	public void addRelativeResource(Long appId, Long envId, Long resId, Long resEnvId, String itemValue) {
		List<String> itemList = Arrays.asList(itemValue.split(","));
		AppEnvResEnv aereSel = new AppEnvResEnv();
		aereSel.setAppId(appId);
		aereSel.setAppEnvId(envId);
		aereSel.setResId(resId);
		// aereSel.setResEnvId(resEnvId);
		List<AppEnvResEnv> selectList = appEnvResEnvMapper.select(aereSel);
		for (AppEnvResEnv aere : selectList) {
			appEnvResEnvMapper.delete(aere);
			BindItem biDel = new BindItem();
			biDel.setAppenvResId(aere.getId());
			bindItemMapper.delete(biDel);
		}
		aereSel.setId(IdGen.get().nextId());
		aereSel.setResEnvId(resEnvId);
		appEnvResEnvMapper.insert(aereSel);
		for (String s : itemList) {
			BindItem bi = new BindItem();
			bi.setId(IdGen.get().nextId());
			bi.setAppenvResId(aereSel.getId());
			bi.setItemId(Long.parseLong(s));
			bindItemMapper.insert(bi);
		}
	}

	public Map<Long, Long> getRelativeResourceSure(Long appId, Long envId) {
		Map<Long, Long> mapResult = new HashMap<Long, Long>();
		AppEnvResEnv aereSel = new AppEnvResEnv();
		aereSel.setAppId(appId);
		aereSel.setAppEnvId(envId);
		List<AppEnvResEnv> list = appEnvResEnvMapper.select(aereSel);
		for (AppEnvResEnv ar : list) {
			// if (mapResult.containsKey((ar.getResId()).toString())) {
			// Map<String, Object> map =
			// mapResult.get(ar.getResId().toString());
			// map.put(ar.getResEnvId().toString(), true);
			// } else {
			// Map<String, Object> map = new HashMap<String, Object>();
			// map.put(ar.getResEnvId().toString(), true);
			// mapResult.put(ar.getResId().toString(), map);
			// }
			mapResult.put(ar.getResId(), ar.getResEnvId());
		}
		return mapResult;
	}

	public Map<String, Map<String, Object>> getRelativeResource(Long appId, Long envId) {
		Map<String, Map<String, Object>> mapResult = new HashMap<String, Map<String, Object>>();
		AppEnvResEnv aereSel = new AppEnvResEnv();
		aereSel.setAppId(appId);
		aereSel.setAppEnvId(envId);
		List<AppEnvResEnv> list = appEnvResEnvMapper.select(aereSel);
		for (AppEnvResEnv ar : list) {
			if (mapResult.containsKey((ar.getResId()).toString())) {
				Map<String, Object> map = mapResult.get(ar.getResId().toString());
				map.put(ar.getResEnvId().toString(), true);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(ar.getResEnvId().toString(), true);
				mapResult.put(ar.getResId().toString(), map);
			}
		}
		return mapResult;
	}

	public JsonPage<ItemVo> getBindItem(Long appId, Long envId, Long resId, Long resEnvId) {
		List<ItemVo> envItem = itemService.getEnvItem(resId, resEnvId);
		List<Item> list = itemService.getBindItem(appId, envId, resId, resEnvId);
		List<Long> bindItem = new ArrayList<Long>();
		for (Item item : list) {
			bindItem.add(item.getId());
		}
		for (ItemVo itemVo : envItem) {
			if (bindItem.contains(itemVo.getId())) {
				itemVo.setBind(true);
			}
		}
		JsonPage result = new JsonPage();
		result.setPage(1);
		result.setTotal(new Long(envItem.size()));
		result.setRows(envItem);
		return result;
	}

	public Map<String, Map<String, Object>> getBindItem(Long appId, Long envId) {
		Map<String, Map<String, Object>> mapResult = new HashMap<String, Map<String, Object>>();
		AppEnvResEnv aereSel = new AppEnvResEnv();
		aereSel.setAppId(appId);
		aereSel.setAppEnvId(envId);
		List<AppEnvResEnv> list = appEnvResEnvMapper.select(aereSel);
		for (AppEnvResEnv ar : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Item> itemList = itemService.getBindItem(appId, envId, ar.getResId(), ar.getResEnvId());
			for (Item item : itemList) {
				map.put(item.getId().toString(), true);
			}
			mapResult.put(ar.getResEnvId().toString(), map);
		}
		return mapResult;
	}
}
