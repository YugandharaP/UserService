package com.bridgelabz.userservice.utilservice.sqsmailservice;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.bridgelabz.userservice.model.Email;
import com.google.gson.Gson;

@Service
public class MessageService {
	@Autowired
	private JmsTemplate jmsTemplate;
	@Value("${queue.name}")
	private String queueName;
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

	public void sendMessage(final Email email) {
		System.out.println("queue name : "+queueName);
		Gson gson = new Gson();
		String message = gson.toJson(email);
		
		jmsTemplate.send(queueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
		
		LOGGER.info("message send successfully from consumer");
	}
}