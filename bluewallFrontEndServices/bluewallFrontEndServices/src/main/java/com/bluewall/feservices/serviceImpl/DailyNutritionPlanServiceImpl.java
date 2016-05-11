package com.bluewall.feservices.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.dao.DailyNutritionPlanDao;
import com.bluewall.feservices.service.DailyNutritionPlanService;
import com.bluewall.util.bean.UserDailyNutritionPlan;

@Service
public class DailyNutritionPlanServiceImpl implements DailyNutritionPlanService{
	
	
	@Autowired
	DailyNutritionPlanDao dailyNutritionPlanDao;
	
	@Override
	public UserDailyNutritionPlan getDailyNutritionPlan(int userID) {

		return dailyNutritionPlanDao.getDailyNutritionPlan(userID);
		
	}

}
