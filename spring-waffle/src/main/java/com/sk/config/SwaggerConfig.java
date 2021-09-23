package com.sk.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger config for spring boot application.
 *
 * @author Sandeep Kumar
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	/**
	 * Docket.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket docket() {
		return new Docket((DocumentationType.SWAGGER_2))
				.select()
				.apis(RequestHandlerSelectors.any())
				.build()
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(
						new ResponseMessageBuilder()
						.code(500)
						.message("Internal Server Error - 500")
						.responseModel(new ModelRef("string"))
						.build(),
						new ResponseMessageBuilder()
						.code(403)
						.message("Forbidden!")
						.build()
						))
				.apiInfo(apiInfo());
	}
	
	/**
	 * Api info.
	 *
	 * @return the api info
	 */
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Waffle Service",
				"This is a microservice running with Spring to provide the user interface for the application",
				"This user interface is not intended for operation users",
				"Terms of services",
				new Contact("Admin", "www.sk.com", "sk@sk.com"),
				"License of API",
				"www.sk.com",
				Collections.emptyList()
				);
	}

}
