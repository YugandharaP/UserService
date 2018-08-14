package com.bridgelabz.userservice.utilservice.mailservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bridgelabz.userservice.model.Email;


/**
 * @author yuga
 * @since 06/08/2018
 *        <p>
 * 		<b>To provide implementation for produce message interface.</b>
 *        </p>
 *
 */
@Service
public class ProducerImplementation implements IMailProducer {
	Logger logger = LoggerFactory.getLogger(ProducerImplementation.class);
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Value("${userservice.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${userservice.rabbitmq.rountingkey}")
	private String routingKey;

	/**@param to
	 * @param subject
	 * @param body
	 *<p><b> To produce message</b></p>
	 */
	@Override
	public void produceMessage(Email email) {
			amqpTemplate.convertAndSend(exchange, routingKey, email);
			logger.info("message sent : with "+email);
	}

}
