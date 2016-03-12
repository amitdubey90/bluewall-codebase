package com.bluewall.feservices.dao;

import java.util.List;

import com.bluewall.util.bean.UserFoodLog;

public interface FoodDao {
	
	public List<UserFoodLog> getUserFoodLog(int userID);
	
}
