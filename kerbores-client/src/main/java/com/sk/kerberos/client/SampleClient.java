package com.sk.kerberos.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SampleClient {

	@Value("${app.access-url}")
	private String endpoint;

	private RestTemplate restTemplate;

	public SampleClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getData() {
		return restTemplate.getForObject(endpoint, String.class);
	}
}