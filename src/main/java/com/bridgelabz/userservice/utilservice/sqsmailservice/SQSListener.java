package com.bridgelabz.userservice.utilservice.sqsmailservice;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.MessagingException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.userservice.model.Email;
import com.bridgelabz.userservice.utilservice.exceptions.ToDoExceptions;
import com.bridgelabz.userservice.utilservice.mailservice.IEmailService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

@Component
public class SQSListener implements MessageListener {
	private static final org.slf4j.Logger LOGGER =LoggerFactory.getLogger(SQSListener.class);

	@Autowired
	IEmailService emailService;
	
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		System.out.println("inside in SQS listner");
		Gson gson=new Gson();
		Email email = null;
		try {
			LOGGER.info("Received message " + textMessage.getText());
			System.out.println("inside try block to send mail");
			email = gson.fromJson(textMessage.getText(),Email.class);
			emailService.sendEmail(email);
		} catch (ToDoExceptions | MessagingException | JsonSyntaxException | JMSException e) {
			LOGGER.error("Error processing message ", e);
		}
	}

}
