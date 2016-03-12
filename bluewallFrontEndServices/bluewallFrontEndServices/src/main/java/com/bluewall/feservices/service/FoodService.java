package com.bluewall.feservices.service;

import java.util.List;

import com.bluewall.util.bean.UserFoodLog;

public interface FoodService {
	
	public List<UserFoodLog> getUserFoodLog(int userID);
	
}
