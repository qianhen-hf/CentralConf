package com.huang.centralconf.client.config;

import java.util.List;
import java.util.Set;

/**
 * @author huangfan
 */
public interface PropertyConfig {

	public String getProperty(String key);
	
	public void setProperty(String key,String value);

	public void addChangeListener(ConfigChangeListener listener);

	public Set<String> getPropertyNames();

	public List<ConfigChangeListener> getChangeListener();
}
