package com.bluewall.feservices.service;

import com.bluewall.util.bean.UserDailyNutritionPlan;


public interface DailyNutritionPlanService {
	
	public UserDailyNutritionPlan getDailyNutritionPlan(int userID);

}
