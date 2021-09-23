package com.sk.kerberos.client;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleClientManualTest {

	@Autowired
	private SampleClient sampleClient;

	@Test
	public void givenKerberizedRestTemplate_whenServiceCall_thenSuccess() {
		assertEquals("data from kerberized server", sampleClient.getData());
	}

	@Test
	public void givenRestTemplate_whenServiceCall_thenFail() {
		sampleClient.setRestTemplate(new RestTemplate());
		assertThrows(RestClientException.class, sampleClient::getData);
	}
}