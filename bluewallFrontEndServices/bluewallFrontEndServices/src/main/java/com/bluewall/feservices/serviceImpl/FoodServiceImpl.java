package com.bluewall.feservices.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.dao.FoodDao;
import com.bluewall.feservices.service.FoodService;
import com.bluewall.util.bean.UserFoodLog;

@Service
public class FoodServiceImpl implements FoodService{

	@Autowired
	FoodDao foodDao;
	
	@Override
	public List<UserFoodLog> getUserFoodLog(int userID) {
		// TODO Auto-generated method stub
		return foodDao.getUserFoodLog(userID);
	}
	
}
