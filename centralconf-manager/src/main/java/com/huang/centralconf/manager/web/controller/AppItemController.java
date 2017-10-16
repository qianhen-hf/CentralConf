package com.huang.centralconf.manager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huang.centralconf.core.common.WebResponse;
import com.huang.centralconf.manager.entry.vo.ItemVo;
import com.huang.centralconf.manager.entry.vo.MyPage;
import com.huang.centralconf.manager.service.AppService;
import com.huang.centralconf.manager.service.EnvService;
import com.huang.centralconf.manager.service.ItemService;
import com.huang.centralconf.manager.util.base.Const;

@Controller
@RequestMapping("/qianhen/manager")
public class AppItemController {
  @Autowired
  ItemService itemService;
  @Autowired
  EnvService envService;
  @Autowired
  AppService appService;


  @ResponseBody
  @RequestMapping(value = "/getAppItem")
  public WebResponse getEnvItem(Long id, Long envId, MyPage myPge) {
    return new WebResponse(0, Const.DATA_SUCCEED, itemService.getEnvItem(id, envId, myPge));
  }

  @ResponseBody
  @RequestMapping(value = "/addAppItem", method = {RequestMethod.POST})
  public WebResponse addItem(ItemVo itemVo) {
    ItemVo itemVoOld = itemService.getItemByName(itemVo);
    if (itemVoOld != null) {
      return new WebResponse(-1, Const.EXIST, null);
    }
    itemService.addItem(itemVo);
    return new WebResponse(0, Const.SAVE_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/updateItem", method = {RequestMethod.POST})
  public WebResponse updateItem(ItemVo itemVo) {
    itemService.updateItem(itemVo);
    return new WebResponse(0, Const.SAVE_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/delAppItem", method = {RequestMethod.POST})
  public WebResponse delItem(Long id) {
    itemService.delItem(id);
    return new WebResponse(0, Const.DEL_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/getCommonItem")
  public WebResponse getCommonItem(@RequestParam Long appId, MyPage myPage) {
    return new WebResponse(0, Const.DATA_SUCCEED, itemService.getCommonItem(appId, myPage));
  }

  @ResponseBody
  @RequestMapping(value = "/addCommonItem", method = {RequestMethod.POST})
  public WebResponse addCommonItem(Long appId, ItemVo itemVo) {
    ItemVo itemVoOld = itemService.getItemByName(itemVo);
    if (itemVoOld != null) {
      return new WebResponse(-1, Const.EXIST, null);
    }
    itemService.addCommonItem(appId, itemVo);
    return new WebResponse(0, Const.SAVE_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/updateCommonItem", method = {RequestMethod.POST})
  public WebResponse updateCommonItem(Long appId, ItemVo itemVo) {
    itemService.updateCommonItem(appId, itemVo);
    return new WebResponse(0, Const.SAVE_SUCCEED, null);
  }

  @ResponseBody
  @RequestMapping(value = "/delCommonItem", method = {RequestMethod.POST})
  public WebResponse delCommonItem(Long id) {
    itemService.delCommonItem(id);
    return new WebResponse(0, Const.DEL_SUCCEED, null);
  }
 
}
