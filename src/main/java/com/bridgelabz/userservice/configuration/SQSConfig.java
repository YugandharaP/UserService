package com.bridgelabz.userservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;

@Configuration
public class SQSConfig {
	@Value("${queue.url}")
	private String url;

	@Bean
	public AmazonSQSClient createSQSClient() {
		AmazonSQSClient amazonSQSClient = new AmazonSQSClient(
				new BasicAWSCredentials("****", "****"));
		amazonSQSClient.setEndpoint(url);
		return amazonSQSClient;
	}
}
