package com.bluewall.feservices.service;

import java.util.List;

import com.bluewall.util.bean.UserDailyNutritionPlan;


public interface DailyNutritionPlanService {
	
	public List<UserDailyNutritionPlan> getDailyNutritionPlan(int userID);

}
