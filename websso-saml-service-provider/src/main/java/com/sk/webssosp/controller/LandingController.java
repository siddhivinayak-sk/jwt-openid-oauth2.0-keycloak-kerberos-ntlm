package com.sk.webssosp.controller;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sk.webssosp.config.AppConfig;
import com.sk.webssosp.model.AppUser;
import com.sk.webssosp.stereotype.CurrentUser;

/**
 * @author Sandeep Kumar
 */
@Controller
public class LandingController {

	private static final Logger LOG = LoggerFactory.getLogger(LandingController.class);

	@Autowired
	AppConfig appConfig;

	@GetMapping("/landing")
	public String landing(@CurrentUser AppUser user, Model model, HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			LOG.debug("Current authentication instance from security context is null");
		else
			LOG.debug("Current authentication instance from security context: {}", this.getClass().getSimpleName());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("domainList", appConfig.getDomains());
		model.addAttribute("removeEntityID", getRemoteEntityId(user.getAttribute("RemoteEntityID")));

		String domain = (String) session.getAttribute("domain");
		if (Objects.nonNull(domain)) {
			String appUrl = appConfig.getUrl().get(user.getPassword());
			return "redirect:" + appUrl;
		}
		return "pages/landing";
	}
	
	private String getRemoteEntityId(String removeEntityIdName) {
		String retVal = removeEntityIdName;
		for(Map.Entry<String, String> entry:appConfig.getIdpLebels().entrySet()) {
			if(entry.getValue().equalsIgnoreCase(removeEntityIdName)) {
				retVal = entry.getKey();
				break;
			}
		}
		return retVal;
	}
}
