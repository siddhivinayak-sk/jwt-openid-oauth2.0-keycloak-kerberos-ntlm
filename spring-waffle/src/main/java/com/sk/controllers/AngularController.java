package com.sk.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * This is angular controller class to provide support 
 * end points for angular ui.
 *
 * @author Sandeep Kumar
 */
@Controller
public class AngularController {

	/**
	 * This end point works as router into the application.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "/**/{[path:[^\\.]*}")
	public String redirect() {
		String returnStr = "forward:/";
		return returnStr;
	}
}