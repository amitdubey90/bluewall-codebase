package com.bluewall.feservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bluewall.feservices.service.FoodService;
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
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserServices userService;
	
	@Autowired
	private FoodService foodService;

	/**
	 * Returns a view for registration form, allowing the user to manually fill
	 * in details or pull the information from social connection providers
	 * 
	 * @return View - registration
	 */
	@RequestMapping("/social")
	public ModelAndView getRegistrationView() {

		UserProfile profile = new UserProfile();
		ModelAndView model = new ModelAndView();
		model.addObject("userProfileData", profile);
		model.setViewName("registration");
		return model;
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public String processRegistration(@RequestBody UserProfile profile) { 
		int userID = 0;
		if (null != profile) {
			userID = userService.createUser(profile);
		}

		log.info("Now calculating daily nutrient plan for user id: " + userID);

		UserDailyNutritionPlan dailyPlan = new UserDailyNutritionPlan();
		double dailyCalorieRequirement;

		float targetWeight;
		
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

		log.info("Nutrient plan created for user id: " + userID);

		userService.createNutrientPlan(dailyPlan, userID);
		
		return "deviceDashboard";
	}

}
