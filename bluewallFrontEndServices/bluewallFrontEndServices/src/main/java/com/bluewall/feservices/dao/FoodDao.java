package com.bluewall.feservices.dao;

import java.util.List;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.util.bean.UserFood;

public interface FoodDao {
	
	public List<UserFood> getUserFoodLog(int userID);
	
	public void createFoodPlate(UserFood createFood, int userID);

	public List<FoodInfo> getFoodInfo(String foodName);
}
