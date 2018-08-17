package com.bridgelabz.userservice.utilservice.mailservice;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.userservice.model.Email;
import com.bridgelabz.userservice.utilservice.exceptions.ToDoExceptions;


/**
 * @author yuga
 * @since 06/08/2018
 * <p><b>To provide implementation for consumer logic to receive mail from producer</b></p>
 *
 */
@Service
public class ConsumerImplementation implements IMailConsumer{
	Logger logger = LoggerFactory.getLogger(ProducerImplementation.class);

	@Autowired
	IEmailService emailService;
	/**To receive mail from producer
	 * @throws ToDoExceptions 
	 * */
	
	@Override
	@RabbitListener(queues = "${todoapplication.rabbitmq.queue}")
	public void recievedMessage(Email email) throws MessagingException, ToDoExceptions {
			logger.info("Recieved Message with mail: " + email);
			emailService.sendEmail(email);
			logger.info("send mail");
		}
}

