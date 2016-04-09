package com.bluewall.feservices.dao;

import java.util.List;

import com.bluewall.util.bean.UserDailyNutritionPlan;

public interface DailyNutritionPlanDao {
	
	public List<UserDailyNutritionPlan> getDailyNutritionPlan(int userID);
	
}
