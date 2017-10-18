package com.huang.centralconf.configService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huang.centralconf.configService.entry.po.App;
import com.huang.centralconf.configService.mapper.AppMapper;

@SuppressWarnings("ALL")
@Service
public class AppService {
	@Autowired
	AppMapper appMapper;

	public App getAppByName(String appName) {
		App condition = new App();
		condition.setAppName(appName);
		App app = appMapper.selectOne(condition);
		if (app == null) {
			return null;
		}
		return app;
	}
}
