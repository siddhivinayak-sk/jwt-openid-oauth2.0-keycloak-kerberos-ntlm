package com.sk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.sk.config.JpaConfig;
import com.sk.config.SwaggerConfig;
import com.sk.config.WebConfig;
 

/**
 * This is application boot class to bootstrap the 
 * application.
 *
 * @author Sandeep Kumar
 */
@EnableDiscoveryClient
@SpringBootApplication
@Import({WebConfig.class, JpaConfig.class, SwaggerConfig.class})
@ComponentScan("com.sk")
public class ApplicationBoot {

	/**
	 * Application main method.
	 *
	 * @param args Takes commandline as argument
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}
}
