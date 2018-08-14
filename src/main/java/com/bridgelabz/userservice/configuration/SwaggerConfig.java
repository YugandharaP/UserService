package com.bridgelabz.userservice.configuration;

import static com.google.common.base.Predicates.or;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;


/**
 * @author yuga
 * @since 13/07/2018
 *<p><b>To provide configuration of swagger.
 *Swagger is widely used for visualizing APIs, and with Swagger UI 
 *it provides online sandbox for frontend developers.</b></p> */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * @return
	 */
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").apiInfo(apiInfo()).select()
				.paths(postPaths()).build();
	}

	/**
	 * @return
	 */
	private Predicate<String> postPaths() {
		return or(regex("/.*"), regex("/.*"));
	}

	/**
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("ToDoAppln").description("Notes Taking using Spring Boot ANd MongoDB ")
				.contact("dharaparanjape.1007@gmail.com").version("1.0").build();
	}
	
	/**
	 * @return
	 */
	@Bean
    public SecurityConfiguration securityConfig() {
        return new SecurityConfiguration(null, null, null, null, "Token", ApiKeyVehicle.HEADER, "token", ",");
    }
	
}

