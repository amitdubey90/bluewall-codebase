package com.bluewall.util.utility;

public class ProteinUtil {
	
	/**
	 * Calculates daily protein intake based on weight
	 * 
	 * The American Dietetic Association (ADA) recommends daily protein intake
	 * for healthy adults as .8-1.0 g of protein/kg body weight. 
	 *  
	 *  1kg = 2.2 Pounds
	 *  
	 * @param weight
	 * @return array with min and max protein intake in grams
	 */
	
	public static int[] calculateDailyProteinInGrams(int weightInPounds){
		int minProteinIntake = 0;
		int maxProteinIntake = 0;
		
		minProteinIntake =  (int) (convertPoundToKg(weightInPounds) * 0.8);
		maxProteinIntake = (int) (convertPoundToKg(weightInPounds) * 1.0);
		
		return new int[] {minProteinIntake , maxProteinIntake};
	}
	
	public static double convertPoundToKg(int weightInPounds){
		
		return (weightInPounds / 2.2) ;
	}
	
}
