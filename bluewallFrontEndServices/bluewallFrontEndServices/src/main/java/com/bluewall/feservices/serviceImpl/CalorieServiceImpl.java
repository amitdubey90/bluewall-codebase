package com.bluewall.feservices.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.dao.CalorieDao;
import com.bluewall.feservices.service.CalorieService;

@Service
public class CalorieServiceImpl implements CalorieService {

	@Autowired
	CalorieDao calorieDao;
	
	@Override
	public int getSumCaloriesBurnt(String userID) {
		return calorieDao.getSumCaloriesBurnt(userID);
	}

	@Override
	public int getSumCaloriesConsumed(String userID) {
		return calorieDao.getSumCaloriesConsumed(userID);
	}

}
