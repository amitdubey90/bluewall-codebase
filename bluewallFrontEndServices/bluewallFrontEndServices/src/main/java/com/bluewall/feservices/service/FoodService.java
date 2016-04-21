package com.bluewall.feservices.service;

import java.util.List;

import com.bluewall.util.bean.UserFood;

public interface FoodService {
	
	/*
	 * returns list of objects for user food logs for a given user id.
	 */
	
	public List<UserFood> getUserFoodLog(int userID);
	
	/*
	 * Create food activity for a user
	 */
	
	public void createFoodPlate(UserFood createFood, int userID);

	public List<UserFood> getFoodInfo(String foodName);
}
