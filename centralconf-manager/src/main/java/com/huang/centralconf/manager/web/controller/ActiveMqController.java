package com.huang.centralconf.manager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.huang.centralconf.manager.entry.po.Mail;
import com.huang.centralconf.manager.service.ActiveMqService;

@SuppressWarnings("ALL")
@Controller
public class ActiveMqController {
	@Autowired
	ActiveMqService activeMqService;
//	@Autowired
//	DubboInteface dubboInteface;

	@ResponseBody
	@RequestMapping("/sendMessage")
	public void sendMessage() {
		Mail mail = new Mail();
		mail.setContent("你好啊,起床了,邮件发送成功");
		mail.setSubject("起床");
		mail.setTo("417303817@qq.com");
		activeMqService.sendMessage(mail);
	}

//	@ResponseBody
//	@RequestMapping("/dubbo")
//	public void dubbo() {
//		String s = dubboInteface.test("springboot");
//		System.out.println(s);
//	}
}
