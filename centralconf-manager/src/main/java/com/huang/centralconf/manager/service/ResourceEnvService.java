package com.huang.centralconf.manager.service;

import java.util.List;

import com.huang.centralconf.manager.entry.vo.JsonPage;
import com.huang.centralconf.manager.entry.vo.MyPage;
import com.huang.centralconf.manager.entry.vo.ResEnvVo;

@SuppressWarnings("ALL")
public interface ResourceEnvService {

	public JsonPage<ResEnvVo> getEnv(Long resId, Long id, Integer type, MyPage myPage);

	public ResEnvVo getEnvByName(Long resId, String envName);

	public void addenv(ResEnvVo resEnvVo);

	public void delEnv(Long envId);
	
	public ResEnvVo getEnvById(Long envId);

	public List<ResEnvVo> getAllEnvByResId(Long resId);

}
