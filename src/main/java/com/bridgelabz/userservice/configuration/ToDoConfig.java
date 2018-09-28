package com.bridgelabz.userservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author yuga
 * @since 06/08/2018
 *        <p>
 *        <b>To configure the beans which is required in the todo
 *        application</b>
 *        </p>
 */
@Configuration
public class ToDoConfig {
	/**
	 * <p>
	 * <b>To create bean of PasswordEncoder interface. Service method for encoding
	 * passwords. The preferred implementation is BCryptPasswordEncoder.</b>
	 * </p>
	 * 
	 * @return password encoder object
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	/**
	 * <p>
	 * <b>ModelMapper - Performs object mapping, maintains</b>
	 * </p>
	 * 
	 * @return model mapper object
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}

