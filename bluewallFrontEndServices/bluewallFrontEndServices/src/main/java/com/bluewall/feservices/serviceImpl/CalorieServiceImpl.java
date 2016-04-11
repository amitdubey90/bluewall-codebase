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
	public int getSumCaloriesBurnt(int userID, String date) {
		return calorieDao.getSumCaloriesBurnt(userID, date);
	}

	@Override
	public int getSumCaloriesConsumed(int userID, String date) {
		return calorieDao.getSumCaloriesConsumed(userID,date);
	}

	@Override
	public int getTargetWeight(int userID, String date) {
		return calorieDao.getTargetWeight(userID,date);
	}

}
