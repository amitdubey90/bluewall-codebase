package com.bluewall.feservices.util;


public class Queries {
	public static final String GET_USER_DETAILS = "Select userID, deviceID from UserConnectedDevice";
	public static final String GET_ACTIVITY_LOG_DETAILS = "select max(activityLogID) as activityLogID from userDatabase.ActivityLog";
	public static final String GET_TOTAL_CALORIE_BURNT = "select sum(caloriesBurnt) as caloriesBurnt from ActivityLog";
	public static final String GET_TOTAL_CALORIE_CONSUMED = "select sum(weightConsumed) as weightConsumed from FoodLog";
	public static final String INS_USER_ACTIVITY_LOG = "insert into ActivityLog(userID, distance, "
														+ "startTime, loggedFrom, duration, caloriesBurnt, activityID)"
														+ " values(?, ?, ?, ?, ?, ?, ?)";
	public static final String INS_USER_FOOD_LOG = "insert into FoodLog(userID, type, "
													+ "foodID, weightConsumed, timeConsumed, loggedFrom, calories, foodLogTime)"
													+ " values(?, ?, ?, ?, ?, ?, ?, ?)";
}
