package com.huang.centralconf.manager.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huang.centralconf.core.common.YtException;
import com.huang.centralconf.manager.entry.po.AppEnv;
import com.huang.centralconf.manager.entry.po.UserAppEnv;
import com.huang.centralconf.manager.entry.vo.AppEnvVo;
import com.huang.centralconf.manager.entry.vo.JsonPage;
import com.huang.centralconf.manager.entry.vo.MyPage;
import com.huang.centralconf.manager.exception.YtfmUserErrors;
import com.huang.centralconf.manager.mapper.AppEnvMapper;
import com.huang.centralconf.manager.mapper.UserAppEnvMapper;
import com.huang.centralconf.manager.service.EnvService;
import com.huang.centralconf.manager.util.ConvertUtil;
import com.huang.centralconf.manager.util.IdGen;

@SuppressWarnings("ALL")
@Service
@Transactional
public class EnvServiceImpl implements EnvService {
	@Autowired
	AppEnvMapper appEnvMapper;
	@Autowired
	UserAppEnvMapper userAppEnvMapper;
	@Autowired
	Environment env;

	@Override
	@Override
	public JsonPage<AppEnvVo> getEnv(Long appId, Long id, Integer type, MyPage myPage) {
		Page<AppEnvVo> page = PageHelper.startPage(myPage.getPage(), myPage.getResults(), true);
		JsonPage<AppEnvVo> resultList = new JsonPage<AppEnvVo>(page);
		String common = getCommonEnvName();
		List<AppEnv> envList = appEnvMapper.geEnv(appId, id, type, common);
		resultList.setRows(ConvertUtil.copyTo(envList, AppEnvVo.class));
		return resultList;
	}

	@Override
	@Override
	public AppEnvVo getEnvByName(Long appId, String envName) {
		AppEnv appEnvWhere = new AppEnv();
		appEnvWhere.setAppId(appId);
		appEnvWhere.setEnvName(envName);
		AppEnv appEnv = appEnvMapper.selectOne(appEnvWhere);
		if (appEnv == null) {
			return null;
		}
		AppEnvVo appEnvVo = new AppEnvVo();
		try {
			BeanUtils.copyProperties(appEnv,appEnvVo);
		} catch (Exception e) {
			throw new YtException(YtfmUserErrors.COPY_PROPRER);
		}
		return appEnvVo;
	}

	@Override
	@Override
	public void addenv(AppEnvVo appEnvVo) {
		AppEnv appEnv = new AppEnv();
		try {
			BeanUtils.copyProperties(appEnvVo,appEnv);
		} catch (Exception e) {
			throw new YtException(YtfmUserErrors.COPY_PROPRER);
		}
		appEnv.setId(IdGen.get().nextId());
		appEnv.setCreateTime(System.currentTimeMillis());
		appEnvMapper.insert(appEnv);
	}

	@Override
	@Override
	public void delEnv(Long appId, Long envId) {
		appEnvMapper.deleteByPrimaryKey(envId);
		UserAppEnv userAppEnv = new UserAppEnv();
		userAppEnv.setEnvId(envId);
		userAppEnvMapper.delete(userAppEnv);
	}

	@Override
	@Override
	public Long createCommonEnv(Long appId) {
		AppEnv appEnv = new AppEnv();
		appEnv.setEnvName(this.getCommonEnvName());
		appEnv.setId(IdGen.get().nextId());
		appEnv.setCreateTime(System.currentTimeMillis());
		appEnv.setAppId(appId);
		appEnvMapper.insert(appEnv);
		return appEnv.getId();

	}

	@Override
	@Override
	public String getCommonEnvName() {
		String common = "_common";
		common = env.getProperty("env.common");
		return common;
	}

	@Override
	public AppEnvVo getEnvById(Long id) {
		AppEnv appEnv = appEnvMapper.selectByPrimaryKey(id);
		AppEnvVo appEnvVo = new AppEnvVo();
		try {
			BeanUtils.copyProperties(appEnv,appEnvVo);
		} catch (Exception e) {
			throw new YtException(YtfmUserErrors.COPY_PROPRER);
		}
		return appEnvVo;
	}
}
