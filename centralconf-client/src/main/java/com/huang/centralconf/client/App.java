package com.huang.centralconf.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huang.centralconf.client.base.Const;
import com.huang.centralconf.client.util.HttpClientService;


@SpringBootApplication
@EnableConfigurationProperties
public class App {
	public static void injectionProperty(HttpClientService httpClientService) {
		Map<String, String> urlparams = new HashMap<String, String>();
		urlparams.put("appName", Const.APP_NAME);
		urlparams.put("envName", Const.ENV_NAME);
		String httpPost = httpClientService.httpPost(Const.HTTP_URL, urlparams);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, Object> readValue = objectMapper.readValue(httpPost, Map.class);
			ArrayList<Map<String, String>> arrayList = (ArrayList) readValue.get("data");
			if (readValue.get("code").equals("0")) {
				for (Map<String, String> map : arrayList) {
					System.setProperty(map.get("itemName"), map.get("itemValue"));
				}
			} else {
				throw new Exception(readValue.get("msg").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
  public static void main(String[] args) {
     SpringApplication.run(App.class, args);
  }
}
