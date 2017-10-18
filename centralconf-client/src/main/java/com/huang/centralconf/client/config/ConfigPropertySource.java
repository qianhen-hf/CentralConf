package com.huang.centralconf.client.config;

import java.util.Set;

import org.springframework.core.env.EnumerablePropertySource;


@SuppressWarnings("ALL")
public class ConfigPropertySource extends EnumerablePropertySource<PropertyConfig> {
  private static final String[] EMPTY_ARRAY = new String[0];

  public ConfigPropertySource(String name, PropertyConfig source) {
    super(name, source);
  }

  @Override
  public String[] getPropertyNames() {
    Set<String> propertyNames = this.source.getPropertyNames();
    if (propertyNames.isEmpty()) {
      return EMPTY_ARRAY;
    }
    return propertyNames.toArray(new String[propertyNames.size()]);
  }

  @Override
  public Object getProperty(String name) {
    return this.source.getProperty(name);
  }
}
