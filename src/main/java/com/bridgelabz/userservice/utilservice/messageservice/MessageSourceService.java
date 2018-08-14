package com.bridgelabz.userservice.utilservice.messageservice;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/**@since 30/07/2018
 * <p>
 * <b>This class is used for resolving messages,with support for the
 * parameterization and internationalization of such messages.</b>
 * </p>
 * @author yuga
 */
@Component
public class MessageSourceService {

	@Autowired
	private MessageSource messageSource;

	private static MessageSourceAccessor accessor;

	@PostConstruct
	private void init() {
		accessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
	}

	/**
	 * @param code
	 * <p>To return particular message for particular code</p>
	 * @return message
	 */
	public static String getMessage(String code) {
		return accessor.getMessage(code);
	}

}
