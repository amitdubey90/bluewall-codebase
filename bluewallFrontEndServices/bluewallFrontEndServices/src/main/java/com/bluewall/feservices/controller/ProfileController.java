package com.bluewall.feservices.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.feservices.service.ProfileService;
import com.bluewall.util.bean.UserProfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class ProfileController {
	
	@Autowired
	ProfileService profileService;
	
	@RequestMapping(value = "/viewprofile", method = RequestMethod.GET)
	@ResponseBody
	public UserProfile getUserProfile(HttpSession session){
		
		int userID;
		UserProfile userProfile = new UserProfile();
		log.info("Calling view profile");
		
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userID = principal.getUserID();
			log.info ("Fetching profile data for user id: " + userID);
			userProfile = profileService.getUserProfile(userID);
		}
		
		log.info("Service successfully executed");
		return userProfile;
	}

}
