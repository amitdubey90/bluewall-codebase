package com.bluewall.feservices.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.dao.FoodDao;
import com.bluewall.feservices.service.FoodService;
import com.bluewall.util.bean.UserFood;

@Service
public class FoodServiceImpl implements FoodService{

	@Autowired
	FoodDao foodDao;
	
	@Override
	public List<UserFood> getUserFoodLog(int userID) {
		return foodDao.getUserFoodLog(userID);
	}

	@Override
	public void createFoodPlate(UserFood createFood, int userID) {
			foodDao.createFoodPlate(createFood, userID);
	}
	
}
