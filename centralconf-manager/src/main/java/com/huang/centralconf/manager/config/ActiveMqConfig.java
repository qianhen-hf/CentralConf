package com.huang.centralconf.manager.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMqConfig {
	
	public ActiveMQConnectionFactory getActiveMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL("tcp://192.168.0.101:61616");
		activeMQConnectionFactory.setUserName("admin");
		activeMQConnectionFactory.setPassword("admin");
		return activeMQConnectionFactory;
	}

	
	public PooledConnectionFactory getPooledConnectionFactory() {
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setConnectionFactory(getActiveMQConnectionFactory());
		pooledConnectionFactory.setMaxConnections(10);
		return pooledConnectionFactory;
	}

	public SingleConnectionFactory getSingleConnectionFactory() {
		SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory();
		singleConnectionFactory.setTargetConnectionFactory(getPooledConnectionFactory());
		return singleConnectionFactory;
	}
	
	@Bean
	public JmsTemplate getJmsTemplate(){
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(getSingleConnectionFactory());
		jmsTemplate.setDefaultDestinationName("mailQueue");
		return jmsTemplate;
	}
	
	

}
