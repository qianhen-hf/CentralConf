package com.huang.centralconf.client.config;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;


public class PropertySourcesProcessor implements BeanFactoryPostProcessor, EnvironmentAware {
	private static final String PROPERTY_SOURCE_NAME = "PropertySources";
	private static final AtomicBoolean PROPERTY_SOURCES_INITIALIZED = new AtomicBoolean(false);

	private ConfigurableEnvironment environment;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (!PROPERTY_SOURCES_INITIALIZED.compareAndSet(false, true)) {
			// already initialized
			return;
		}
		initializePropertySources();
	}

	protected void initializePropertySources() {
		CompositePropertySource composite = new CompositePropertySource(PROPERTY_SOURCE_NAME);
		PropertyConfig propertyConfig = ConfigManager.getInstance().getConfig();
		composite.addPropertySource(new ConfigPropertySource("property", propertyConfig));
		environment.getPropertySources().addFirst(composite);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = (ConfigurableEnvironment) environment;
	}

	// @Override
	// public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry
	// registry) throws BeansException {
	//
	// }

}
