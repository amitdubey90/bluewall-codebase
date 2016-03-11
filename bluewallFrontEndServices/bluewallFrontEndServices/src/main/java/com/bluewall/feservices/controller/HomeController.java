package com.bluewall.feservices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fitnessApp")
public class HomeController {

	@RequestMapping("/welcome")
	public String populateUserDashBoard() {
	
		return "welcomePage";
	}
}
