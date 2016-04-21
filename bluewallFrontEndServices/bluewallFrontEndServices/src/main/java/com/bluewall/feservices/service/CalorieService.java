package com.bluewall.feservices.service;

public interface CalorieService {

	public int getSumCaloriesBurnt(int userID, String date);
	
	public int getSumCaloriesConsumed(int userID, String date);
	
	public int getTargetWeight(int userID);
}
