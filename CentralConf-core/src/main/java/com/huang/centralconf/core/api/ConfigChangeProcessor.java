package com.huang.centralconf.core.api;

public interface ConfigChangeProcessor {

  public void configChange(String key, String oldValue, String newValue);

}
