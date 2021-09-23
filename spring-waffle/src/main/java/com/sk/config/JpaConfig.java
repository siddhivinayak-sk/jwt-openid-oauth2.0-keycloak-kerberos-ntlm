package com.sk.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA configuration file for
 * Spring boot application.
 *
 * @author Sandeep Kumar
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.sk")
public class JpaConfig {

	/**
	 * Transaction manager.
	 *
	 * @param emf the emf
	 * @return the platform transaction manager
	 */
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager jpat = new JpaTransactionManager();
		jpat.setEntityManagerFactory(emf);
		return jpat;
	}
}
