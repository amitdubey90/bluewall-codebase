package com.bluewall.feservices.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.feservices.service.DailyNutritionPlanService;
import com.bluewall.util.bean.UserDailyNutritionPlan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class DailyNutritionPlanController {

	@Autowired
	DailyNutritionPlanService dailyNutritionPlanService;

	@RequestMapping(value = "/dailyNutritionPlan", method = RequestMethod.GET)
	@ResponseBody
	public UserDailyNutritionPlan getDailyNutritionPlan(HttpSession session) {

		int userID = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userID = principal.getUserID();
			UserDailyNutritionPlan userDailyPlan = new UserDailyNutritionPlan();

			userDailyPlan = dailyNutritionPlanService.getDailyNutritionPlan(userID);
			log.info("Now displaying daily nutrition chart");
			return userDailyPlan;
		}
		return null;
	}

}
