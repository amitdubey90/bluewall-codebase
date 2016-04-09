package com.bluewall.feservices.dao;


import java.util.List;
import com.bluewall.util.bean.UserDailyNutritionPlan;
import com.bluewall.feservices.bean.UserPrincipal;

import com.bluewall.util.bean.UserProfile;

public interface UserDao {


	public int createUser(UserProfile user);
	
	public List<UserProfile> getUserDetails(int userID);
	
	public void createNutrientPlan(UserDailyNutritionPlan dailyPlan, int userID);

	UserPrincipal loadUserByName(String emailID);

}
