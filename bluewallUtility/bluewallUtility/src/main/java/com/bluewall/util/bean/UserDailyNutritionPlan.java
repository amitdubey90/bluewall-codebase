package com.bluewall.util.bean;

import lombok.Data;

@Data
public class UserDailyNutritionPlan {

	double dailyCalories;

	double fatInGms;
	double fatInCalories;
	double fatConsumed;

	double proteinInGms;
	double proteinInCalories;
	double proteintConsumed;

	double carbInGms;
	double carbInCalories;
	double carbsConsumed;
}
