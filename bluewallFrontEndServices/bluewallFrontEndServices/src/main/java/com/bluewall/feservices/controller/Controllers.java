package com.bluewall.feservices.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bluewall.feservices.service.UserServices;
import com.bluewall.util.bean.UserProfile;

@Controller
public class Controllers {

	@Autowired
	private UserServices userService;

	@RequestMapping("/register")
	public ModelAndView getRegistrationView() {

		UserProfile profile = new UserProfile();
		ModelAndView model = new ModelAndView();
		model.addObject("userProfileData", profile);
		model.setViewName("registration");
		return model;
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String processRegistration(@Valid @ModelAttribute("userProfileData") UserProfile profile) {

		if (null!= profile) {
			userService.createUser(profile);
		}

		return "deviceDashboard";
	}

}
