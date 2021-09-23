package com.sk.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import waffle.servlet.spi.BasicSecurityFilterProvider;
import waffle.servlet.spi.NegotiateSecurityFilterProvider;
import waffle.servlet.spi.SecurityFilterProvider;
import waffle.servlet.spi.SecurityFilterProviderCollection;
import waffle.spring.NegotiateSecurityFilter;
import waffle.spring.NegotiateSecurityFilterEntryPoint;
import waffle.windows.auth.impl.WindowsAuthProviderImpl;

/**
 * This class provide the configuration for waffle.
 *
 * @author Sandeep Kumar
 */
@Configuration
public class WaffleConfig {

	/**
	 * Waffle windows auth provider.
	 *
	 * @return the windows auth provider impl
	 */
	@Bean
	public WindowsAuthProviderImpl waffleWindowsAuthProvider() {
		return new WindowsAuthProviderImpl();
	}

	/**
	 * Negotiate security filter provider.
	 *
	 * @param windowsAuthProvider the windows auth provider
	 * @return the negotiate security filter provider
	 */
	@Bean
	public NegotiateSecurityFilterProvider negotiateSecurityFilterProvider(
			WindowsAuthProviderImpl windowsAuthProvider) {
		return new NegotiateSecurityFilterProvider(windowsAuthProvider);
	}

	/**
	 * Basic security filter provider.
	 *
	 * @param windowsAuthProvider the windows auth provider
	 * @return the basic security filter provider
	 */
	@Bean
	public BasicSecurityFilterProvider basicSecurityFilterProvider(WindowsAuthProviderImpl windowsAuthProvider) {
		return new BasicSecurityFilterProvider(windowsAuthProvider);
	}

	/**
	 * Waffle security filter provider collection.
	 *
	 * @param negotiateSecurityFilterProvider the negotiate security filter provider
	 * @param basicSecurityFilterProvider the basic security filter provider
	 * @return the security filter provider collection
	 */
	@Bean
	public SecurityFilterProviderCollection waffleSecurityFilterProviderCollection(
			NegotiateSecurityFilterProvider negotiateSecurityFilterProvider,
			BasicSecurityFilterProvider basicSecurityFilterProvider) {
		SecurityFilterProvider[] securityFilterProviders = { negotiateSecurityFilterProvider,
				basicSecurityFilterProvider };
		return new SecurityFilterProviderCollection(securityFilterProviders);
	}

	/**
	 * Negotiate security filter entry point.
	 *
	 * @param securityFilterProviderCollection the security filter provider collection
	 * @return the negotiate security filter entry point
	 */
	@Bean
	public NegotiateSecurityFilterEntryPoint negotiateSecurityFilterEntryPoint(
			SecurityFilterProviderCollection securityFilterProviderCollection) {
		NegotiateSecurityFilterEntryPoint negotiateSecurityFilterEntryPoint = new NegotiateSecurityFilterEntryPoint();
		negotiateSecurityFilterEntryPoint.setProvider(securityFilterProviderCollection);
		return negotiateSecurityFilterEntryPoint;
	}

	/**
	 * Waffle negotiate security filter.
	 *
	 * @param securityFilterProviderCollection the security filter provider collection
	 * @return the negotiate security filter
	 */
	@Bean
	public NegotiateSecurityFilter waffleNegotiateSecurityFilter(
			SecurityFilterProviderCollection securityFilterProviderCollection) {
		NegotiateSecurityFilter negotiateSecurityFilter = new NegotiateSecurityFilter();
		negotiateSecurityFilter.setProvider(securityFilterProviderCollection);
		return negotiateSecurityFilter;
	}

	// This is required for Spring Boot so it does not register the same filter
	// twice

	/**
	 * Waffle negotiate security filter registration.
	 *
	 * @param waffleNegotiateSecurityFilter the waffle negotiate security filter
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean waffleNegotiateSecurityFilterRegistration(
			NegotiateSecurityFilter waffleNegotiateSecurityFilter) {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(waffleNegotiateSecurityFilter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

}