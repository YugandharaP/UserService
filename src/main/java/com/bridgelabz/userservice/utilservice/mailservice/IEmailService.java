package com.bridgelabz.userservice.utilservice.mailservice;

import javax.mail.MessagingException;

import com.bridgelabz.userservice.model.Email;
import com.bridgelabz.userservice.utilservice.exceptions.ToDoExceptions;


/**
 * @author yuga
 * @since 13/07/2018
 *<p></b>To Send Mail</b></p>
 */
public interface IEmailService {
	/**
	 * @param email
	 * @throws MessagingException
	 * <p><b> send mail to the intended user</b></p>
	 */
	public void sendEmail( Email email) throws ToDoExceptions, MessagingException;
}
