package com.huang.centralconf.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huang.centralconf.client.base.Const;
import com.huang.centralconf.client.util.HttpClientService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.huang.centralconf.client.annotation.AnnotationProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "datasource")
public class Config {
	@Bean
	public AnnotationProcessor getAnnotationProcessor(){
		return new AnnotationProcessor();
	}
	
	@Bean
	public PropertySourcesProcessor getPropertySourcesProcessor(){
		return new PropertySourcesProcessor();
	}

	@Bean
	public HttpClientService getHttpClientService() {
		HttpClientService httpClientService = new HttpClientService();
		injectionProperty(httpClientService);
		return httpClientService;
	}

	public void injectionProperty(HttpClientService httpClientService) {
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
					ConfigManager instance = ConfigManager.getInstance();
					PropertyConfig config = instance.getConfig();
					config.setProperty(map.get("itemName"), map.get("itemValue"));
				}
			} else {
				throw new Exception(readValue.get("msg").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
