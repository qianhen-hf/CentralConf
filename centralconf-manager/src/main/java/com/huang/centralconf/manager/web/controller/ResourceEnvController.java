package com.huang.centralconf.manager.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.huang.centralconf.core.common.WebResponse;
import com.huang.centralconf.manager.entry.po.User;
import com.huang.centralconf.manager.entry.vo.JsonPage;
import com.huang.centralconf.manager.entry.vo.MyPage;
import com.huang.centralconf.manager.entry.vo.ResEnvVo;
import com.huang.centralconf.manager.entry.vo.ResourceVo;
import com.huang.centralconf.manager.service.ResourceEnvService;
import com.huang.centralconf.manager.service.ResourceService;
import com.huang.centralconf.manager.service.UserService;
import com.huang.centralconf.manager.util.base.Const;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/qianhen/manager")
public class ResourceEnvController {
	@Autowired
	UserService userService;
	@Autowired
	ResourceService resourceService;
	@Autowired
	ResourceEnvService resourceEnvService;

	@RequestMapping("/toResourceEnv")
	public ModelAndView toSourceEnv(Long resId) {
		ModelAndView mv = new ModelAndView();
		ResourceVo resourceVo = resourceService.getResourceById(resId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resId", resId.toString());
		map.put("resName", resourceVo.getResName());
		mv.addObject("resource", map);
		mv.setViewName("resource/resourceEvnList");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/getSourceEnv")
	public WebResponse getSourceEnv(Long resId, MyPage myPage, HttpSession session) throws Exception {
		User sessionUser = (User) session.getAttribute(Const.SESSION_USER);
		JsonPage<ResEnvVo> env = resourceEnvService.getEnv(resId, sessionUser.getId(), sessionUser.getType(), myPage);
		return new WebResponse(0, Const.DATA_SUCCEED, env);
	}

	@ResponseBody
	@RequestMapping(value = "/copySourceEnv")
	public WebResponse copyEnv(@RequestParam String app, @RequestParam String srcEnv, @RequestParam String dstEnv,
			@RequestParam boolean isOverrie) {
		return new WebResponse(0, Const.OPER_SUCCEED, null);
	}

	@ResponseBody
	@RequestMapping(value = "/delSourceEnv", method = { RequestMethod.POST })
	public WebResponse delSourceEnv(Long id) {
		resourceEnvService.delEnv(id);
		WebResponse wrs = new WebResponse(0, Const.DEL_SUCCEED, null);
		return wrs;
	}

	@ResponseBody
	@RequestMapping(value = "/addSourceEnv", method = { RequestMethod.POST })
	public WebResponse addEnv(ResEnvVo resEnvVo) {
		ResEnvVo envByName = resourceEnvService.getEnvByName(resEnvVo.getResId(), resEnvVo.getEnvName());
		if (envByName != null) {
			return new WebResponse(-1, Const.EXIST, null);
		}
		resourceEnvService.addenv(resEnvVo);
		WebResponse wrs = new WebResponse(0, Const.SAVE_SUCCEED, null);
		return wrs;
	}
}
