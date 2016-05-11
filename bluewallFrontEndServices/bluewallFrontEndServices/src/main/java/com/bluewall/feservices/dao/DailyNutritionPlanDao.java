package com.bluewall.feservices.dao;

import com.bluewall.util.bean.UserDailyNutritionPlan;

public interface DailyNutritionPlanDao {
	
	UserDailyNutritionPlan getDailyNutritionPlan(int userID);
}
