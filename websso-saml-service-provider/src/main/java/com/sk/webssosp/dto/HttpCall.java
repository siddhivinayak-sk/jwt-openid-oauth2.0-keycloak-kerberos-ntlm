package com.sk.webssosp.dto;

import java.util.Map;

/**
 * 
 * @author Sandeep Kumar
 *
 */
public class HttpCall {

	private String name;
	private String httpUrl;
	private String httpMethod;
	private Map<String, String> headers;
	private Map<String, String> queryParameters;
	private String requestTemplate;
	private String responseTemplate;
	private boolean isKeyMaterial;
	private String keyMaterialType;
	private String keyMaterialPath;
	private String keyMaterialSecret;
	private boolean isTrustMaterial;
	private String trustMaterialType;
	private String trustMaterialPath; 
	private String trustMaterialSecret;
	private boolean diableCookieManagement;
	private boolean setCustomConnectionManager;
	
	
	public String getHttpUrl() {
		return httpUrl;
	}
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public Map<String, String> getQueryParameters() {
		return queryParameters;
	}
	public void setQueryParameters(Map<String, String> queryParameters) {
		this.queryParameters = queryParameters;
	}
	public boolean isKeyMaterial() {
		return isKeyMaterial;
	}
	public void setKeyMaterial(boolean isKeyMaterial) {
		this.isKeyMaterial = isKeyMaterial;
	}
	public String getKeyMaterialType() {
		return keyMaterialType;
	}
	public void setKeyMaterialType(String keyMaterialType) {
		this.keyMaterialType = keyMaterialType;
	}
	public String getKeyMaterialPath() {
		return keyMaterialPath;
	}
	public void setKeyMaterialPath(String keyMaterialPath) {
		this.keyMaterialPath = keyMaterialPath;
	}
	public String getKeyMaterialSecret() {
		return keyMaterialSecret;
	}
	public void setKeyMaterialSecret(String keyMaterialSecret) {
		this.keyMaterialSecret = keyMaterialSecret;
	}
	public boolean isTrustMaterial() {
		return isTrustMaterial;
	}
	public void setTrustMaterial(boolean isTrustMaterial) {
		this.isTrustMaterial = isTrustMaterial;
	}
	public String getTrustMaterialType() {
		return trustMaterialType;
	}
	public void setTrustMaterialType(String trustMaterialType) {
		this.trustMaterialType = trustMaterialType;
	}
	public String getTrustMaterialPath() {
		return trustMaterialPath;
	}
	public void setTrustMaterialPath(String trustMaterialPath) {
		this.trustMaterialPath = trustMaterialPath;
	}
	public String getTrustMaterialSecret() {
		return trustMaterialSecret;
	}
	public void setTrustMaterialSecret(String trustMaterialSecret) {
		this.trustMaterialSecret = trustMaterialSecret;
	}
	public boolean isDiableCookieManagement() {
		return diableCookieManagement;
	}
	public void setDiableCookieManagement(boolean diableCookieManagement) {
		this.diableCookieManagement = diableCookieManagement;
	}
	public boolean isSetCustomConnectionManager() {
		return setCustomConnectionManager;
	}
	public void setSetCustomConnectionManager(boolean setCustomConnectionManager) {
		this.setCustomConnectionManager = setCustomConnectionManager;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRequestTemplate() {
		return requestTemplate;
	}
	public void setRequestTemplate(String requestTemplate) {
		this.requestTemplate = requestTemplate;
	}
	public String getResponseTemplate() {
		return responseTemplate;
	}
	public void setResponseTemplate(String responseTemplate) {
		this.responseTemplate = responseTemplate;
	}
}
