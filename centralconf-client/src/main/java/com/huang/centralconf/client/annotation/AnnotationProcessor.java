package com.huang.centralconf.client.annotation;

import com.huang.centralconf.client.config.ConfigChangeEvent;
import com.huang.centralconf.client.config.ConfigChangeListener;
import com.huang.centralconf.client.config.ConfigManager;
import com.huang.centralconf.client.config.PropertyConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("ALL")
public class AnnotationProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        processFields(bean, clazz.getDeclaredFields());
        processMethods(bean, clazz.getDeclaredMethods());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private void processFields(Object bean, Field[] declaredFields) {

    }

    private void processMethods(final Object bean, Method[] declaredMethods) {
        for (final Method method : declaredMethods) {
            PropertyConfigChangeListener annotation = AnnotationUtils.findAnnotation(method, PropertyConfigChangeListener.class);
            if (annotation == null) {
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            ReflectionUtils.makeAccessible(method);
            PropertyConfig config = ConfigManager.getInstance().getConfig();
            config.addChangeListener(new ConfigChangeListener() {
                @Override
                public void onChange(ConfigChangeEvent changeEvent) {
                    ReflectionUtils.invokeMethod(method, bean, changeEvent);
                }
            });
        }
    }

}
