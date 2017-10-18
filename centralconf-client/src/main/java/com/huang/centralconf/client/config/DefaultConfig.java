package com.huang.centralconf.client.config;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

@SuppressWarnings("ALL")
public class DefaultConfig implements PropertyConfig {
	private static final Logger logger = LoggerFactory.getLogger(DefaultConfig.class);
	private List<ConfigChangeListener> listeners = Lists.newCopyOnWriteArrayList();
	private Map<String,String> property = new ConcurrentHashMap<String,String>();
	
	@Override
	public String getProperty(String key) {
		return this.property.get(key);
	}

	@Override
	public void addChangeListener(ConfigChangeListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public Set<String> getPropertyNames() {
		return property.keySet();
	}

	@Override
	public List<ConfigChangeListener> getChangeListener() {
		return listeners;
	}

	@Override
	public void setProperty(String key, String value) {
		property.put(key, value);
	}


	
}
