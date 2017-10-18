package com.huang.centralconf.manager.service;

import com.huang.centralconf.manager.entry.vo.AppVo;
import com.huang.centralconf.manager.entry.vo.JsonPage;
import com.huang.centralconf.manager.entry.vo.MyPage;

@SuppressWarnings("ALL")
public interface AppService {

	public JsonPage<AppVo> getAllAppByPage(MyPage myPage, Long userId, Integer type);

//	public List<App> getAllApp();

	public void createApp(AppVo AppVo);

	public AppVo getAppById(Long id);

	public AppVo getAppByName(String appName);

	public void delApp(Long id);

	public void updateApp(AppVo AppVo);

}
