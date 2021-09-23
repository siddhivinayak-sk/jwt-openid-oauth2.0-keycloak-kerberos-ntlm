package com.sk.webssosp.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.sk.webssosp.config.AppConfig;
import com.sk.webssosp.dto.HttpCall;
import com.sk.webssosp.utils.RestUtil;


/**
 * 
 * @author Sandeep Kumar
 *
 */
@Service
public class HttpCallService {

	private static final Logger LOGGER = LogManager.getLogger(HttpCallService.class);
	
	private Map<String, RestTemplate> restTemplates = new HashMap<>();
	
	@Autowired
	public HttpCallService(AppConfig appConfig) {
		for(HttpCall call:appConfig.getRedirectauth()) {
			RestTemplate restTemplate = RestUtil.getRestTemplate(
					call.isKeyMaterial(),
					call.getKeyMaterialType(),
					call.getKeyMaterialPath(),
					call.getKeyMaterialSecret(),
					call.isTrustMaterial(),
					call.getTrustMaterialType(),
					call.getTrustMaterialPath(),
					call.getTrustMaterialSecret(),
					call.isDiableCookieManagement(),
					call.isSetCustomConnectionManager()
					);
			restTemplates.put(call.getName(), restTemplate);
		}
	}
	
	public String makeCall(HttpCall call, String username, String domain, String relayState) {
		String finalURL = null;
		try {
			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			if(null != call.getQueryParameters()) {
				call.getQueryParameters().entrySet().stream().forEach(entry -> queryParams.add(entry.getKey(), entry.getValue()));
			}
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(call.getHttpUrl()).queryParams(queryParams);
			URI uri = new URI(builder.toUriString());
			finalURL = uri.toString();
			
			String requestTemplate = call.getRequestTemplate();
			requestTemplate = requestTemplate.replace("$username", username);
			requestTemplate = requestTemplate.replace("$domain", domain);
			requestTemplate = requestTemplate.replace("$relayState", relayState);
			
			RestTemplate restTemplate = restTemplates.get(call.getName());
			
			byte[] data = requestTemplate.getBytes();

			MultiValueMap<String, String> headers = new HttpHeaders();
			if(null != call.getHeaders()) {
				call.getHeaders().entrySet().stream().forEach(entry -> headers.add(entry.getKey(), entry.getValue()));
			}
			RequestEntity<byte[]> requestEntity = new RequestEntity<>(data, headers, HttpMethod.resolve(call.getHttpMethod().toUpperCase()), uri, byte[].class);
			ResponseEntity<byte[]> responseEntity = restTemplate.exchange(requestEntity, byte[].class);
			return writeResponse(call.getResponseTemplate(), finalURL, responseEntity.getBody(), responseEntity.getHeaders(), responseEntity.getStatusCode().value());
		}
		catch(HttpClientErrorException ex) {
			LOGGER.error("EX Response", ex);
			return writeResponse(call.getResponseTemplate(), finalURL, ex.getResponseBodyAsByteArray(), ex.getResponseHeaders(), ex.getRawStatusCode());
		}
		catch(Exception ex) {
			LOGGER.error("Exception:", ex);
			return null;
		}
	}
	
	private String writeResponse(String responseTemplate, String url, byte[] body, HttpHeaders httpHeaders, int responseCode) {
		LOGGER.trace("URL: {}", url);
		LOGGER.trace("Status: {}", responseCode);
		if(null != httpHeaders) {
			LOGGER.trace("Response Header: ");
			httpHeaders.entrySet().stream().forEach(entry -> {
				LOGGER.trace(entry.getKey() + "{}", entry.getValue().toString());
			});
		}
		String response = new String(body);
		if(null != responseTemplate) {
			return responseTemplate.replace("$response", response);
		}
		else {
			LOGGER.trace("Response: {}", response);
			return new String(body);
		}
	}
}
