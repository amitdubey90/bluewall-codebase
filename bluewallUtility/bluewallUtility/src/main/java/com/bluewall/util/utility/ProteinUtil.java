package com.bluewall.util.utility;

public class ProteinUtil {
	
	/**
	 * Calculates daily protein intake based on daily calorie
	 *  
	 * Calculation is based on following statistics:
	 * 	1. 1gm = 4 calories
	 * 	2. Protein is 15% of daily calorie need
	 *  
	 * @param dailyCalorieNeed
	 * 
	 * @return Protein in grams
	 * @return Protein in calories
	 */
	
	public double calculateDailyProteinInGrams(double dailyCalorieNeed){
			
		return ((calculateDailyProteinInCalories(dailyCalorieNeed)) / 4) ;
	}
	
	public double calculateDailyProteinInCalories(double dailyCalorieNeed){
		
		return (dailyCalorieNeed * 0.15) ;
	}
	
}
