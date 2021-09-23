package com.sk.controllers;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller provides end points to the angular code
 * the list of URLs which will futher used for calling other
 * end points.
 *
 * @author Sandeep Kumar
 */
@RestController
public class UrlController {

	
	/**
	 * This end point provides the list of urls.
	 *
	 * @return Returns map of URLs
	 */
	@GetMapping("/getURLs")
	public HashMap<String, Object> get() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("SecurityURL", "");
		return map;
	}

}
