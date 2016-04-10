package com.bluewall.util.utility;

public class CarbsUtil {
	
	/**
	 * 
	 * Calculates daily carbohydrates intake based on daily calorie
	 * 
	 * Calculation is based on following statistics:
	 * 	1. 1gm = 4 calories
	 * 	2. Carbohydrates is 55% of daily calorie need
	 * 
	 * @param dailyCalorieNeed
	 * 
	 * @return carbohydrates intake in grams
	 * @return carbohydrates intake in calories
	 */
	
	
	public static double calculateDailyCarbohydratesInGrams(double dailyCalorieNeed){
		
		return ((calculateDailyCarbohydratesInCalories(dailyCalorieNeed)) / 4) ;
	}
	
	public static double calculateDailyCarbohydratesInCalories(double dailyCalorieNeed){
		
		return (dailyCalorieNeed * 0.55);
	}

}
