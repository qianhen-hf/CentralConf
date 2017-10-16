package com.huang.centralconf.manager.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.huang.centralconf.core.common.WebResponse;
import com.huang.centralconf.manager.entry.po.User;
import com.huang.centralconf.manager.entry.vo.AppVo;
import com.huang.centralconf.manager.entry.vo.JsonPage;
import com.huang.centralconf.manager.entry.vo.MyPage;
import com.huang.centralconf.manager.service.AppService;
import com.huang.centralconf.manager.service.UserService;
import com.huang.centralconf.manager.util.base.Const;

@RestController
@RequestMapping("/qianhen/manager")
public class AppController {
	@Autowired
	UserService userService;
	@Autowired
	AppService appService;

	@RequestMapping(value = "/getApp")
	public WebResponse getAllApp(HttpSession session, MyPage myPage) throws Exception {
		User sessionUser = (User) session.getAttribute(Const.SESSION_USER);
		JsonPage<AppVo> appList = appService.getAllAppByPage(myPage, sessionUser.getId(), sessionUser.getType());
		WebResponse wb = new WebResponse(0, Const.DATA_SUCCEED, appList);
		return wb;
	}

	@RequestMapping(value = "/addApp", method = { RequestMethod.POST })
	public WebResponse addApp(AppVo appVo, HttpSession session) {
		AppVo appVoResult = appService.getAppByName(appVo.getAppName());
		if (null != appVoResult) {
			// 已经存在这个应用
			return new WebResponse(0, Const.EXIST, null);
		}
		User sessionUser = (User) session.getAttribute(Const.SESSION_USER);
		appVo.setCreateUserId(sessionUser.getId());
		appService.createApp(appVo);
		return new WebResponse(0, Const.SAVE_SUCCEED, null);
	}

	@RequestMapping(value = "/delApp", method = { RequestMethod.POST })
	public WebResponse delApp(Long id) {
		appService.delApp(id);
		WebResponse wrs = new WebResponse(0, Const.DEL_SUCCEED, null);
		return wrs;
	}
	
	@RequestMapping(value = "/updateApp", method = { RequestMethod.POST })
	public WebResponse updateApp(AppVo appVo) {
		appService.updateApp(appVo);
		WebResponse wrs = new WebResponse(0, Const.UPDATE_SUCCEED, null);
		return wrs;
	}
}
