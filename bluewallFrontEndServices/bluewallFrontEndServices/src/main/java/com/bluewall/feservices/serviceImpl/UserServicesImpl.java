package com.bluewall.feservices.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.dao.UserDao;
import com.bluewall.feservices.service.UserServices;
import com.bluewall.util.bean.UserDailyNutritionPlan;
import com.bluewall.util.bean.UserProfile;

@Service
public class UserServicesImpl implements UserServices{
	
	@Autowired
	UserDao userDao;
	
	@Override
	public int createUser(UserProfile userRegister) {
		return userDao.createUser(userRegister);
	}

	@Override
	public List<UserProfile> getUserDetails(int userID) {
		return userDao.getUserDetails(userID);	
	}

	@Override
	public void createNutrientPlan(UserDailyNutritionPlan dailyPlan, int userID) {
		userDao.createNutrientPlan(dailyPlan, userID);
	}

	
}
