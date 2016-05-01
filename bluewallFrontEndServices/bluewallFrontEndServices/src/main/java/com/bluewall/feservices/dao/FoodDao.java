package com.bluewall.feservices.dao;

import java.util.List;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.util.bean.UserFood;
import com.bluewall.util.bean.UserRating;

public interface FoodDao {
	
	public List<UserFood> getUserFoodLog(int userID);
	
	public void createFoodPlate(UserFood createFood, int userID);

	public List<FoodInfo> getFoodInfo(String foodName);
	
	public void rateFoodItems(UserRating userRating, int userID);
}
