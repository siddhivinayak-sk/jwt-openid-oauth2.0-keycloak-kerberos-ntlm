package com.sk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The Class WebConfig.
 * 
 * @author Sandeep Kumar
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	/** The access control allow origin. */
	@Value("${pas.access_control_allow_origin:}")
	String access_control_allow_origin;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry)
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(access_control_allow_origin).allowedHeaders("*").allowedMethods("*").allowCredentials(true);
	}

}
