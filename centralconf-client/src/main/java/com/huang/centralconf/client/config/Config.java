package com.huang.centralconf.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.huang.centralconf.client.annotation.AnnotationProcessor;

@Configuration
public class Config {
	@Bean
	public AnnotationProcessor getAnnotationProcessor(){
		return new AnnotationProcessor();
	}
	
	@Bean
	public PropertySourcesProcessor getPropertySourcesProcessor(){
		return new PropertySourcesProcessor();
	}
}
