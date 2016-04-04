package com.bluewall.util.utility;

public class ProteinUtil {
	
	/**
	 * Calculates daily protein intake based on daily calorie
	 *  
	 *  1gm = 4 calories
	 *  Protein is 15% of daily calorie need
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
