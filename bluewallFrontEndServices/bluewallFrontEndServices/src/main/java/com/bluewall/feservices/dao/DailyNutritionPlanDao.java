package com.bluewall.feservices.dao;

import com.bluewall.util.bean.UserDailyNutritionPlan;

public interface DailyNutritionPlanDao {
	
	public UserDailyNutritionPlan getDailyNutritionPlan(int userID);
	
}
