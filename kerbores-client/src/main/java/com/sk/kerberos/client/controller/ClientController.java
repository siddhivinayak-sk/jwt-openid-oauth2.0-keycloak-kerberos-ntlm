package com.sk.kerberos.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.kerberos.client.SampleClient;

@RestController
public class ClientController {

	@Autowired
	private SampleClient sampleClient;
	
	@GetMapping("/hello")
	public String get() {
		return sampleClient.getData();
	}
	
}
