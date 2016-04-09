package com.bluewall.feservices.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.service.DailyNutritionPlanService;
import com.bluewall.util.bean.UserDailyNutritionPlan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class DailyNutritionPlanController {
	
	@Autowired
	DailyNutritionPlanService dailyNutritionPlanService;
	
	@RequestMapping(value = "/dailyNutritionPlan/{userID}", method = RequestMethod.GET)
	@ResponseBody
	public void getDailyNutritionPlan(@PathVariable("userID") int userID){
		
		List<UserDailyNutritionPlan> userDailyPlan = new ArrayList<UserDailyNutritionPlan> ();
		
		userDailyPlan = dailyNutritionPlanService.getDailyNutritionPlan(userID);
		
		log.info("Now displaying daily nutrition chart");
		
		for (UserDailyNutritionPlan iterateDailyPlan : userDailyPlan){
			System.out.println("Daily Calories Requirement : " + iterateDailyPlan.getDailyCalories());
			System.out.println("Fat Requirement in gms : " + iterateDailyPlan.getFatInGms());
			System.out.println("Fat Requirement in calories : " + iterateDailyPlan.getFatInCalories());
			System.out.println("Carbohydrates Requirement in gms : " + iterateDailyPlan.getCarbInGms());
			System.out.println("Carbohydrates Requirement in calories: " + iterateDailyPlan.getCarbInCalories());
			System.out.println("Protein Requirement in gms : " + iterateDailyPlan.getProteinInGms());
			System.out.println("Protein Requirement in calories : " + iterateDailyPlan.getProteinInCalories());
		}
		
		log.info("Nutrition Chart displayed");
				
	}
	
}
