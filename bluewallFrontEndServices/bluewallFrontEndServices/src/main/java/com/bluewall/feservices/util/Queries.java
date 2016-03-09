package com.bluewall.feservices.util;


public class Queries {
	public static final String GET_USER_DETAILS = "Select userID, deviceID from UserConnectedDevice";
	public static final String GET_ACTIVITY_LOG_DETAILS = "select max(activityLogID) as activityLogID from userDatabase.ActivityLog";
	public static final String GET_TOTAL_CALORIE_BURNT = "select sum(caloriesBurnt) as caloriesBurnt from ActivityLog";
	public static final String GET_TOTAL_CALORIE_CONSUMED = "select sum(weightConsumed) as weightConsumed from FoodLog";
}