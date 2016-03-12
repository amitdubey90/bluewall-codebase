package com.bluewall.feservices.service;

import java.util.List;

import com.bluewall.util.bean.UserFoodLog;

public interface FoodService {
	
	/*
	 * returns list of objects for user food logs for a given user id.
	 */
	
	public List<UserFoodLog> getUserFoodLog(int userID);
	
}
