package com.bluewall.util.utility;

public class CarbsUtil {
	
	/**
	 * 
	 * Calculates daily carbohydrates intake based on daily calorie
	 * 
	 * 1gm = 4 calories
	 * Carbohydrates is 55% of daily calorie need
	 * 
	 * @param dailyCalorieNeed
	 * 
	 * @return carbohydrates intake in grams
	 * @return carbohydrates intake in calories
	 */
	
	
	public double calculateDailyCarbohydratesInGrams(double dailyCalorieNeed){
		
		return ((calculateDailyCarbohydratesInCalories(dailyCalorieNeed)) / 4) ;
	}
	
	public double calculateDailyCarbohydratesInCalories(double dailyCalorieNeed){
		
		return (dailyCalorieNeed * 0.55);
	}

}
