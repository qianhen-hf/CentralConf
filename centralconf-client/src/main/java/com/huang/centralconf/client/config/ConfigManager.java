package com.huang.centralconf.client.config;

import java.util.Map;

import com.google.common.collect.Maps;

@SuppressWarnings("ALL")
public class  ConfigManager{
	private static ConfigManager configManager = new ConfigManager();
	private Map<String, PropertyConfig> map = Maps.newConcurrentMap();

	private ConfigManager(){
		PropertyConfig config = new DefaultConfig();
		config.setProperty("abcd","adjsagfd");
		this.map.put("propertyConfig",config);
	}
	public static ConfigManager getInstance(){
		return configManager;
	}
	public PropertyConfig getConfig() {
		return this.map.get("propertyConfig");
	}
	
	public PropertyConfig setConfig(PropertyConfig propertyConfig) {
		return this.map.put("propertyConfig",propertyConfig);
	}
}
