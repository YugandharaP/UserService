package com.bridgelabz.userservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
	/*@Value("${spring.profiles.active}")
	static String activeProfile;*/
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

	/**
	 * Add PropertySourcesPlaceholderConfigurer to make placeholder work. This
	 * method MUST be static
	 */
	/*@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		Resource resource;
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();

		// get active profile
		//activeProfile = System.getProperty("spring.profiles.active");

		// choose different property files for different active profile
		if ("development".equals(activeProfile)) {
			resource = new ClassPathResource("development.properties");
		} else if ("test".equals(activeProfile)) {
			resource = new ClassPathResource("test.properties");
		} else {
			resource = new ClassPathResource("production.properties");
		}

		// load the property file
		propertySourcesPlaceholderConfigurer.setLocation(resource);
		return propertySourcesPlaceholderConfigurer;
	}
*/
}

