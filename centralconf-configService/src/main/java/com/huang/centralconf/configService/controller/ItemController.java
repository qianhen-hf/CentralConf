package com.huang.centralconf.configService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huang.centralconf.configService.entry.po.Item;
import com.huang.centralconf.configService.service.ItemService;
import com.huang.centralconf.core.common.WebResponse;

@SuppressWarnings("ALL")
@Controller
public class ItemController {
	@Autowired
	ItemService itemService;

	@RequestMapping("/getAllItem")
	@ResponseBody
	public WebResponse getAllItem(String appName, String envName) {
		List<Item> allItem = itemService.getAllItem(appName, envName);
		return new WebResponse(0, "susscess", allItem);
	}
}
