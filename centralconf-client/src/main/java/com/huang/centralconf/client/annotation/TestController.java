package com.huang.centralconf.client.annotation;


import com.huang.centralconf.client.config.ConfigChangeEvent;
import com.huang.centralconf.client.config.ConfigChangeListener;
import com.huang.centralconf.client.config.ConfigManager;
import com.huang.centralconf.client.config.PropertyConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {
    @Value("${abcd}")
    private String abcd;

    @RequestMapping("/prlint")
    public void prlint() {
        System.out.println(abcd);
    }

    @RequestMapping("/change")
    public void change() {
        System.out.println("112");
        ConfigManager instance = ConfigManager.getInstance();
        PropertyConfig config = instance.getConfig();
        config.setProperty("abcd", "11111222");
        System.out.println("1");
        List<ConfigChangeListener> changeListeners = config.getChangeListener();
        for(ConfigChangeListener changeListener:changeListeners){
            System.out.println("12");
            ConfigChangeEvent configChangeEvent = new ConfigChangeEvent();
            changeListener.onChange(configChangeEvent);
        }
    }

    @PropertyConfigChangeListener
    public void change1(ConfigChangeEvent configChangeEvent) {
        System.out.println("propertyConfigChangeListener");
    }


}


