package com.bridgelabz.userservice.utilservice.mailservice;

import com.bridgelabz.userservice.model.Email;

/**
 * @author yuga
 * @since 19/07/2018
 * <p><b>To produce message with taking consumer address,subject and message body</b></p>
 *
 */
public interface IMailProducer {
	/**
	 * @param to
	 * @param subject
	 * @param body
	 * <p><b>To produce message</b></p>	 */
	public void produceMessage(Email email);
}

