package com.huang.centralconf.manager.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huang.centralconf.manager.entry.po.Mail;

@SuppressWarnings("ALL")
@Service
public class ActiveMqService {
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	JmsTemplate jmsTemplate;

	
	public void sendMessage(final Mail mail){
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				try {
					return session.createTextMessage(objectMapper.writeValueAsString(mail));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

}
