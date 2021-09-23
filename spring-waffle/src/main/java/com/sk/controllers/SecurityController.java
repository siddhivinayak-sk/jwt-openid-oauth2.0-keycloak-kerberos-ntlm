package com.sk.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.sk.dto.Credentials;
 
/**
 * This controller provides end points for login
 * in both ways, by sso and by login screen.
 *
 * @author Sandeep Kumar
 */
@RestController
public class SecurityController {
	
	/**
	 * This end point is used for login with windows based sso.
	 *
	 * @param auth Authentication object
	 * @param res HttpServletRespnse object
	 * @param req HttpServletRequest object
	 * @return Returns response of the end point
	 */
	@PostMapping("/getWindowsUser")
	public ResponseEntity<String> getWindowsUser(Authentication auth, HttpServletResponse res, HttpServletRequest req) {
		String result = "";
		String returnStr = "";
		String authPrincipal = "";
		HttpHeaders responseHeaders = new HttpHeaders();
		RestTemplate rest = new RestTemplate();
		try {
			if (auth.getPrincipal() != null) {
			
				/*String clientIp = getClientIpAddr(req);
				String[] parts = ipRange.split(",");
				for (String part : parts) {
					if(clientIp.contains(part)){
						System.out.println(part+">>>TRUE");
					}else{
						System.out.println(part+">>>FALSE");
					}
				}
				System.out.println(clientIp+">>>clientIp");*/
				
				returnStr = String.format("You are logged in as: %s", auth.getPrincipal());
				authPrincipal = auth.getPrincipal().toString();
				MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
				ResponseEntity<String> strRE = rest.getForEntity(authPrincipal, String.class);
				result = strRE.toString();				
				return new ResponseEntity<>("{\"" + "token" + "\":\"" + result + "\",\"User\":\"" + authPrincipal + "\"}", responseHeaders,HttpStatus.OK);
			} else {
				return new ResponseEntity<>("{\"Status\":\"Problem Exists!!!\"}", responseHeaders,
						HttpStatus.BAD_REQUEST);
			}
		} catch (final Exception e) {
			return new ResponseEntity<>("{\"Status\":\"Problem Exists!!!\"}", responseHeaders,
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Login with user credential.
	 *
	 * @param login Credential object
	 * @param res HttpServletRequest object
	 * @param req HttpServletResponse object
	 * @return Returns response to the caller
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/apilogin") 
	public ResponseEntity<String> login(@RequestBody Credentials login, HttpServletResponse res, HttpServletRequest req) {
	
		String result = "";
		HttpHeaders responseHeaders = new HttpHeaders();
		String username = login.getUsername();
		String password = login.getPassword();
		String domain = login.getDomain();
		String otp = login.getOtp();
		
		try {
			String restURL = "";
			RestTemplate rest = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("j_username", username);
			map.add("j_password", password);
			map.add("otp", otp);
			map.add("org", domain);
			ResponseEntity<String> response = rest.exchange(restURL, HttpMethod.POST, new HttpEntity(map, new HttpHeaders()), String.class);
			result = response.getBody();
			
			/**
			 * This code block has been commented to remove
			 * the details from the security web microservice
			 * instead it will be loaded from the end point
			 */
			/*
			responseHeaders.set(TAS_COOKIE, result);
			res.addCookie(new Cookie(TAS_COOKIE, result));
			String userdetailsUrl = securityUrl + userpermissionurl;
			String decendentUrl = applicationApiUrl + decendentsUrl;
			HttpHeaders requestHeaders = new HttpHeaders();
			//requestHeaders.add("Cookie", TAS_COOKIE + "=" + result);
			requestHeaders.add("Authorization", "Bearer " + result);
			@SuppressWarnings("unchecked")
			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
			ResponseEntity rssResponse = rest.exchange(userdetailsUrl, HttpMethod.GET, requestEntity, String.class);
			@SuppressWarnings("unused")
			ResponseEntity decendentResponse = rest.exchange(decendentUrl, HttpMethod.GET, requestEntity, String.class);
			String rss = (String) rssResponse.getBody();
			String decendent = (String) decendentResponse.getBody();
			return new ResponseEntity<String>("{\"" + "token" + "\":\"" + result + "\",\"UserPermissions\":" + rss + ", \"decendent\": " + decendent + ", \"loggedInUserName\": \"" + username + "\", \"loggedIndomain\": \"" + domain + "\"}", responseHeaders, response.getStatusCode());
			*/
			
			return new ResponseEntity<>("{\"" + "token" + "\":\"" + result + "\", \"loggedInUserName\": \"" + username + "\", \"loggedIndomain\": \"" + domain + "\"}", responseHeaders, response.getStatusCode());
		} catch (final Exception e) {
			if(e instanceof HttpClientErrorException) {
				HttpClientErrorException hce = (HttpClientErrorException)e;
				return new ResponseEntity<>("{\"Status\": \"" + hce.getResponseBodyAsString() + "\" }", responseHeaders, hce.getStatusCode());
			}
			return new ResponseEntity<>("{\"Status\":\"Problem Exists!!!\"}", responseHeaders, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * Helper method used into the windows authorization feature.
	 *
	 * @param request takes HttpServletRequest object
	 * @return return string to the calling environment
	 */
	public static String getClientIpAddr(HttpServletRequest request) {  
    String ip = request.getHeader("X-Forwarded-For");  
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
        ip = request.getHeader("Proxy-Client-IP");  
    }  
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
        ip = request.getHeader("WL-Proxy-Client-IP");  
    }  
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
    }  
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
        ip = request.getHeader("HTTP_X_FORWARDED");  
    }  
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
    }  
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
        ip = request.getHeader("HTTP_CLIENT_IP");  
    }  
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
        ip = request.getHeader("HTTP_FORWARDED_FOR");  
    }  
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
        ip = request.getHeader("HTTP_FORWARDED");  
    }  
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
        ip = request.getHeader("HTTP_VIA");  
    }  
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
        ip = request.getHeader("REMOTE_ADDR");  
    }  
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
        ip = request.getRemoteAddr();  
    }  
    return ip;  
}
}