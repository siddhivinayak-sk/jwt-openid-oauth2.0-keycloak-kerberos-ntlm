package com.sk.webssosp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.webssosp.config.AppConfig;
import com.sk.webssosp.dto.TokenDTO;
import com.sk.webssosp.entity.Token;
import com.sk.webssosp.model.AppUser;
import com.sk.webssosp.service.TokenService;
import com.sk.webssosp.stereotype.CurrentUser;
import com.sk.webssosp.stereotype.UserDomain;

/**
 * @author Sandeep Kumar
 */
@Controller
public class GoToApp {

	private static final Logger LOG = LoggerFactory.getLogger(GoToApp.class);
	
	private static final String SET_COOKIE = "Set-Cookie";

	@Autowired
	TokenService tokenService;

	@Autowired
	AppConfig appUrlConfig;

	@PostMapping("/goto")
	public String goToApp(@CurrentUser AppUser user, @ModelAttribute("formData") UserDomain formData,
			HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			LOG.debug("Current authentication instance from security context is null");
		else
			LOG.debug("Current authentication instance from security context: {}", this.getClass().getSimpleName());

		String domain = formData.getDomain();
		String userID = user.getUsername();
		String username = userID.split("@")[0];
		String appUrl = appUrlConfig.getUrl().get(user.getPassword());
		
		String result = null;
		try {
			result = tokenService.generateToken(username, domain, user.getPassword());
		}
		catch(Exception ex) {
			LOG.info("Security API Error: {}", result);
			return "redirect:error";
		}

		Token token = null;
		try {
			token = tokenService.saveToken(username, domain, result);
			if(null != appUrlConfig.getDirectcall() && appUrlConfig.getDirectcall().booleanValue()) {
				token = new Token();
				token.setIdToken(result);
			}
		} catch (Exception e) {
			LOG.error("Error occurred while saving the token: " + e.getMessage(), e);
			return "redirect:error";
		}
		response.addHeader(SET_COOKIE, "secureid=" + token.getIdToken());
		response.addHeader("x-token-id", token.getIdToken());
		request.getSession().setAttribute("domain", domain);
		return "redirect:" + appUrl + "?s=" + token.getIdToken();
	}

	@PostMapping(value = "/token", produces = "application/json", consumes = "application/json")
	public ResponseEntity<TokenDTO> findTokenById(@RequestBody String data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		TokenDTO getDto = mapper.readValue(data, TokenDTO.class);
		TokenDTO dto = new TokenDTO();
		if (getDto.getIdToken() != null) {
			dto = tokenService.getToken(getDto.getIdToken());
		}
		if (dto.getIdToken() != null) {
			tokenService.deleteToken(dto.getIdToken());
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} else
			return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
	}

	
	@GetMapping("/poll")
	public ResponseEntity<String> goToAppGet() {
		return new ResponseEntity<>("Yes", HttpStatus.OK);
	}
}
