package com.bluewall.feservices.service;

import java.util.List;

import com.bluewall.util.bean.UserDailyNutritionPlan;
import com.bluewall.util.bean.UserProfile;

public interface UserServices {
	
	public int createUser(UserProfile userRegister);
	
	public List<UserProfile> getUserDetails(int userID);
	
	public void createNutrientPlan(UserDailyNutritionPlan dailyPlan, int userID);

}
