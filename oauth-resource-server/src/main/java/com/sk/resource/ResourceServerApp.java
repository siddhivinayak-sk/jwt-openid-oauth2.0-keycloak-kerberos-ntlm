package com.sk.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class ResourceServerApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ResourceServerApp.class, args);
    }
    
	@Bean
    @Primary
    public CorsConfigurationSource corsConfigurationSource() {
        String origins = "*";
        final List<String> allowedOriginslist = new ArrayList<>();
        Optional.ofNullable(origins).ifPresent(origin->  allowedOriginslist.addAll(Arrays.asList(origin.split(","))));
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOriginslist);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type","Authorization","Accept", "branch", "module-name"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    
}
