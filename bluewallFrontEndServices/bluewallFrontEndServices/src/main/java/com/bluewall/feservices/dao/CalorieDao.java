package com.bluewall.feservices.dao;

/**
 * @author Jenil
 *
 */
public interface CalorieDao {
	
	/**
	 * @param userID
	 * @return Sum of Calories Burnt for a day
	 */
	public int getSumCaloriesBurnt(int userID, String date);
	/**
	 * @param userID
	 * @return Sum of Calories Consumed for a day
	 */
	public int getSumCaloriesConsumed(int userID, String date);
	
	public int getTargetWeight(int userID);
}
