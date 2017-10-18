package com.huang.centralconf.manager.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@SuppressWarnings("ALL")
@Configuration
public class MapperConfig {

  @Bean
  public MapperScannerConfigurer mapperScannerConfigurer() {
    MapperScannerConfigurer configure = new MapperScannerConfigurer();
    configure.setBasePackage("com.huang.centralconf.manager.mapper");
    configure.setMarkerInterface(Mapper.class);
    return configure;
  }
  
}
