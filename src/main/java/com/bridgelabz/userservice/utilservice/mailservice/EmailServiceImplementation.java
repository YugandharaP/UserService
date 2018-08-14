package com.bridgelabz.userservice.utilservice.mailservice;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bridgelabz.userservice.model.Email;


/**
 * @author yuga
 *@since 13/07/2018
 *<p><b>To provide implementation for email service to send mail</b></p>
 */
@Service
public class EmailServiceImplementation implements IEmailService {
	@Autowired
	public JavaMailSender emailSender;

	/**@param email 
	 * <p><b>sending mail to the intended user</b></p>
	 * @throws MessagingException 
	 */
	@Override
	public void sendEmail(Email email) throws  MessagingException {
		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(email.getTo());
		helper.setSubject(email.getSubject());
		helper.setText(email.getBody());

		emailSender.send(message);

	}

}
