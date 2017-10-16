package com.huang.centralconf.manager.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.huang.centralconf.core.common.WebResponse;
import com.huang.centralconf.manager.entry.po.User;
import com.huang.centralconf.manager.util.base.Const;

@Controller
@RequestMapping("/qianhen/manager")
public class LoginController {

	@RequestMapping("/login")
	@ResponseBody
	public WebResponse login(String userName, String password) {
		Subject subject = SecurityUtils.getSubject();
		Object principal = subject.getPrincipal();
		if (principal == null) {
			UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
			subject.login(token);
		}
		User user = (User) subject.getSession().getAttribute(Const.SESSION_USER);
		return new WebResponse(0, Const.LOGIN_SUCCEED, user);
	}

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(Const.SUCCESS_LOGIN);
		return mv;
	}

	@RequestMapping("/tologin")
	@ResponseBody
	public WebResponse toLogin() {
		return new WebResponse(401, "未登录", null);
	}

	// @RequestMapping("/ajaxToLogin")
	// @ResponseBody
	// public WebResponse ajaxToLogin() {
	// return new WebResponse(401, Const.NO_SESSION, null);
	// }

	@RequestMapping("/nauthorizedUrl")
	@ResponseBody
	public WebResponse nauthorizedUrl() {
		return new WebResponse(405, Const.NO_AUTHORIZED_MSG, null);
	}

	@RequestMapping("/logout")
	@ResponseBody
	public WebResponse logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		}
		return new WebResponse(0, Const.LOGIN_OUT, null);
	}
}
