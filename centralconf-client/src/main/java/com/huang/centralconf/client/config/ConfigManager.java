package com.huang.centralconf.client.config;

import java.util.Map;

import com.google.common.collect.Maps;

public class  ConfigManager{
	private static ConfigManager configManager = new ConfigManager();
	private Map<String, PropertyConfig> map = Maps.newConcurrentMap();

	private ConfigManager(){
		PropertyConfig config = new DefaultConfig();
		this.map.put("propertyConfig1",config);
	}
	public static ConfigManager getInstance(){
		return configManager;
	}
	public PropertyConfig getConfig() {
		return this.map.get("propertyConfig1");
	}
	
	public PropertyConfig setConfig(PropertyConfig propertyConfig) {
		return this.map.put("propertyConfig",propertyConfig);
	}
}
