package com.huang.centralconf.core;

@SuppressWarnings("ALL")
public class ConfigValue {

  private String value;

  private String desc;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
