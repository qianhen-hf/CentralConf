package com.huang.centralconf.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.huang.cn.dubbo.DubboImple;
import com.huang.cn.dubbo.DubboInteface;

@SuppressWarnings("ALL")
@Configuration
public class DubboConfig {
//	@Bean
//	public RegistryConfig registry() {
//		RegistryConfig registryConfig = new RegistryConfig();
//		registryConfig.setAddress("192.168.76.128:2181,192.168.76.129:2181,192.168.76.130:2181");
//		registryConfig.setProtocol("zookeeper");
//		return registryConfig;
//	}
//
//	@Bean
//	public ApplicationConfig application() {
//		ApplicationConfig applicationConfig = new ApplicationConfig();
//		applicationConfig.setName("testApp");
//		return applicationConfig;
//	}
//
//	// @Bean
//	// public MonitorConfig monitorConfig() {
//	// MonitorConfig mc = new MonitorConfig();
//	// mc.setProtocol("registry");
//	// return mc;
//	// }
//
//	// @Bean
//	// public ReferenceConfig referenceConfig() {
//	// ReferenceConfig rc = new ReferenceConfig();
//	// rc.setMonitor(monitorConfig());
//	// return rc;
//	// }
//
//	@Bean
//	public ProtocolConfig protocol() {
//		ProtocolConfig protocolConfig = new ProtocolConfig();
//		protocolConfig.setPort(20880);
//		return protocolConfig;
//	}
//
//	// @Bean
//	// public ProviderConfig provider() {
//	// ProviderConfig providerConfig = new ProviderConfig();
//	// providerConfig.setMonitor(monitorConfig());
//	// return providerConfig;
//	// }
//
//	// @Bean
//	// public DubboInteface getDubboInteface() {
//	// return new DubboImple();
//	// }
//
//	@Bean
//	public DubboInteface getDubboImple(){
//		return new DubboImple();
//	}
//	
//	@Bean
//	public ServiceBean<DubboInteface> personServiceExport(DubboInteface person) {
//		ServiceBean<DubboInteface> serviceBean = new ServiceBean<DubboInteface>();
////		serviceBean.setProxy("javassist");
//		serviceBean.setVersion("myversion");
//		serviceBean.setInterface(DubboInteface.class.getName());
//		serviceBean.setRef(person);
//		serviceBean.setTimeout(5000);
//		serviceBean.setRetries(3);
//		return serviceBean;
//	}

//	@Bean
//	public ReferenceBean<DubboInteface> person() {
//		ReferenceBean<DubboInteface> ref = new ReferenceBean<>();
//		ref.setVersion("huang");
//		ref.setInterface(DubboInteface.class);
//		ref.setTimeout(5000);
//		ref.setRetries(3);
//		ref.setCheck(false);
//		return ref;
//	}

}
