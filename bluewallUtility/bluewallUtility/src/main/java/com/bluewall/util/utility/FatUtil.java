package com.bluewall.util.utility;

public class FatUtil {
	
	/**
	 * Based on daily calorie requirement, calculates and returns
	 * daily fat needs of a user in grams.
	 * 
	 * Calculation is based on following statistics:
	 * 	1. Fat intake should equal 30% of your total days calories.
	 * 	2. 1 gram of fat = 9 calories 
	 * 
	 * @param dailyCalorieNeed
	 * @return Fat requirement in grams based on daily calorie requirement.
	 *  
	 */
	
	public static int calculateDailyFatNeedsInGrams(float dailyCalorieNeed){
		
		return (int)Math.round((calculateCaloriesFromFat(dailyCalorieNeed)) / 9);
	}
	
	public static double calculateCaloriesFromFat(float dailyCalorieNeed){
		return dailyCalorieNeed * 0.30;
	}
}
