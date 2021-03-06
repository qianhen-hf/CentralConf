package com.huang.centralconf.manager.service;

import com.huang.centralconf.manager.entry.vo.AppEnvVo;
import com.huang.centralconf.manager.entry.vo.JsonPage;
import com.huang.centralconf.manager.entry.vo.MyPage;

public interface EnvService {

	public JsonPage<AppEnvVo> getEnv(Long appId, Long id, Integer type,MyPage myPage) ;

	public AppEnvVo getEnvByName(Long appId, String envName);

	public void addenv(AppEnvVo appEnvVo);

	public void delEnv(Long appId, Long envId);
	
	public String getCommonEnvName();
	
	public Long createCommonEnv(Long appId);

	public AppEnvVo getEnvById(Long id);
}
