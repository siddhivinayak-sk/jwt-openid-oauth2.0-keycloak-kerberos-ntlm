package com.sk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Arrays;


@SpringBootApplication
public class SpringBootHelloWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHelloWorldApplication.class, args);
	}
	
	@Bean
	@Primary
	public CorsConfigurationSource corsConfigurationSource() {
		String origins = "*";
		final List<String> allowedOriginslist = new ArrayList<>();
		Optional.ofNullable(origins).ifPresent(origin -> allowedOriginslist.addAll(Arrays.asList(origin.split(","))));
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(allowedOriginslist);
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Accept"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
}
