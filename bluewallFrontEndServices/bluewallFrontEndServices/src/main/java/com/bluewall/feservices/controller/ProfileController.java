package com.bluewall.feservices.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.feservices.service.ProfileService;
import com.bluewall.feservices.service.UserServices;
import com.bluewall.util.bean.UserDailyNutritionPlan;
import com.bluewall.util.bean.UserProfile;
import com.bluewall.util.common.ActivityLevel;
import com.bluewall.util.common.Gender;
import com.bluewall.util.utility.CalorieUtil;
import com.bluewall.util.utility.CarbsUtil;
import com.bluewall.util.utility.FatUtil;
import com.bluewall.util.utility.ProteinUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class ProfileController {
	
	@Autowired
	ProfileService profileService;
	
	@Autowired
	private UserServices userService;
	
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
	
	@RequestMapping(value = "/updateProfile", method = RequestMethod.PUT)
	@ResponseBody
	public void updateUserProfile(@RequestBody UserProfile profile, HttpSession session){
		
		int userID;
		double dailyCalorieRequirement;
		float targetWeight;
		
		UserDailyNutritionPlan dailyPlan = new UserDailyNutritionPlan();
		
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		log.info("Calling update profile");
		
		if (null != principal) {
			userID = principal.getUserID();
			profileService.updateUserProfile(profile, userID);
			
			if (profile.getTargetWeight() != 0.0)
				targetWeight = profile.getTargetWeight();
			else
				targetWeight = profile.getWeight();
			
			/*
			 * Calculates daily calorie requirement. If user activity level is null,
			 * default = MODERATELY_ACTIVE
			 */

			if (profile.getActivityLevel().equalsIgnoreCase("SEDENTARY"))
				dailyCalorieRequirement = CalorieUtil.calculateDailyCalorieNeeds(targetWeight, profile.getHeight(),
						profile.getAge(), ((profile.getGender().equalsIgnoreCase("MALE")) ? Gender.MALE : Gender.FEMALE),
						ActivityLevel.SEDENTARY);
			else if (profile.getActivityLevel().equalsIgnoreCase("LIGHTLY_ACTIVE"))
				dailyCalorieRequirement = CalorieUtil.calculateDailyCalorieNeeds(targetWeight, profile.getHeight(),
						profile.getAge(), ((profile.getGender().equalsIgnoreCase("MALE")) ? Gender.MALE : Gender.FEMALE),
						ActivityLevel.LIGHTLY_ACTIVE);
			else if (profile.getActivityLevel().equalsIgnoreCase("EXTREMELY_ACTIVE"))
				dailyCalorieRequirement = CalorieUtil.calculateDailyCalorieNeeds(targetWeight, profile.getHeight(),
						profile.getAge(), ((profile.getGender().equalsIgnoreCase("MALE")) ? Gender.MALE : Gender.FEMALE),
						ActivityLevel.EXTREMELY_ACTIVE);
			else if (profile.getActivityLevel().equalsIgnoreCase("VERY_ACTIVE"))
				dailyCalorieRequirement = CalorieUtil.calculateDailyCalorieNeeds(targetWeight, profile.getHeight(),
						profile.getAge(), ((profile.getGender().equalsIgnoreCase("MALE")) ? Gender.MALE : Gender.FEMALE),
						ActivityLevel.VERY_ACTIVE);
			else
				dailyCalorieRequirement = CalorieUtil.calculateDailyCalorieNeeds(targetWeight, profile.getHeight(),
						profile.getAge(), ((profile.getGender().equalsIgnoreCase("MALE")) ? Gender.MALE : Gender.FEMALE),
						ActivityLevel.MODERATELY_ACTIVE);

			dailyPlan.setDailyCalories(dailyCalorieRequirement);
			dailyPlan.setProteinInCalories(ProteinUtil.calculateDailyProteinInCalories(dailyCalorieRequirement));
			dailyPlan.setProteinInGms(ProteinUtil.calculateDailyProteinInGrams(dailyCalorieRequirement));
			dailyPlan.setCarbInCalories(CarbsUtil.calculateDailyCarbohydratesInCalories(dailyCalorieRequirement));
			dailyPlan.setCarbInGms(CarbsUtil.calculateDailyCarbohydratesInGrams(dailyCalorieRequirement));
			dailyPlan.setFatInCalories(FatUtil.calculateDailyFatInCalories(dailyCalorieRequirement));
			dailyPlan.setFatInGms(FatUtil.calculateDailyFatInGrams(dailyCalorieRequirement));

			log.info("Nutrient plan will be updates for user id: " + userID);

			userService.createNutrientPlan(dailyPlan, userID);

		}
	}
	
	

}
