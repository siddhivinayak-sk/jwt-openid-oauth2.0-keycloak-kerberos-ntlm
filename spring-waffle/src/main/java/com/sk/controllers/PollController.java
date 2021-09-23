package com.sk.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sk.constants.BuildConstants;


/**
 * The controller provides end points for providing polling
 * capability to the application.
 *
 * @author Sandeep Kumar
 */
@RestController
public class PollController {
	
	/**
	 * Poll end point.
	 *
	 * @return It return string
	 */
	@GetMapping("/poll")
	public String poll() {
		return "yes";
	}
	
	@GetMapping("/buildinfo")
	public ResponseEntity<Map<String, String>> getBuildInfo() {
		Map<String, String> tmp = new HashMap<String, String>();
		tmp.put("version", BuildConstants.VERSION);
		tmp.put("build", BuildConstants.BUILD);
		return new ResponseEntity<>(tmp, HttpStatus.OK);
	}

	@GetMapping("/urltest")
	public String testURL(@RequestParam(name = "url", required = false, defaultValue = "")String url) {
		return url;
	}
}
