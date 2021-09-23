package com.sk.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is test controller.
 *
 * @author Sandeep Kumar
 */
@RestController
public class TestController {

	/**
	 * Return the current principal name.
	 *
	 * @param auth Authentication object as intpu
	 * @return Return string as response
	 */
	@GetMapping (value="/test")
	public String test(Authentication auth) {
		return String.format("You are logged in as: %s", auth.getPrincipal());
	}
}