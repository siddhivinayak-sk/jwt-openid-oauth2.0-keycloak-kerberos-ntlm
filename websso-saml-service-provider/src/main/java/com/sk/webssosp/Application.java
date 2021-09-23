package com.sk.webssosp;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Sandeep Kumar
 */
@SpringBootApplication 
public class Application extends SpringBootServletInitializer {
    
    @Value("${cors.allowedOrigins}")   
    List<String> allowedOrigins;
    
    @Value("${cors.allowedHeaders}")   
    List<String> allowedHeaders;
    
    @Value("${cors.allowedMethods}")   
    List<String> allowedMethods;
    
    @Override protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
	@Bean 
	@Primary
	public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedHeaders(allowedHeaders);
        config.setAllowedMethods(allowedMethods);
        
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
