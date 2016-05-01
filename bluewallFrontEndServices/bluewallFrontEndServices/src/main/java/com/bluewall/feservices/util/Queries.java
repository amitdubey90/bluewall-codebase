package com.bluewall.feservices.util;

public class Queries {
	public static final String GET_USER_DETAILS = "Select userID, deviceID from UserConnectedDevice";
	public static final String GET_ACTIVITY_LOG_DETAILS = "select max(activityLogID) as activityLogID from userDatabase.ActivityLog";
	public static final String GET_TOTAL_CALORIE_BURNT = "select sum(caloriesBurnt) as caloriesBurnt from ActivityLog";
	public static final String GET_TOTAL_CALORIE_CONSUMED = "select sum(weightConsumed) as weightConsumed from FoodLog";
	public static final String GET_DAILY_CALORIES = "select dailyCalories from UserDailyNutrientPlan";
	public static final String INS_USER_ACTIVITY_LOG = "insert into ActivityLog(userID, name,distance,activityLogDate, "
			+ "duration, caloriesBurnt,loggedFrom,logTime)" + " values(?, ?, ?, ?, ?, ?, ?,?)";
	public static final String INS_USER_FOOD_LOG = "insert into FoodLog(userID, type, foodID, weightConsumed, loggedFrom, calories, logTime, foodLogDate)"
			+ " values(?, ?, ?, ?, ?, ?, ?,?)";
	public static final String INS_USERS = "insert into users(username, password) values(?,?)";
	public static final String INS_USER_GOALS = "insert into UserGoal(userID, goalType, targetWeight,startDate, endDate) "
			+ "values(?, ?, ?, ?, ?)";
	public static final String GET_FOODID = "select foodId from FoodInfo";

	public static final String GET_USER_INFO = "select * from UserInfo";
	public static final String INS_DAILY_NUTRITION_PLAN = "insert into UserDailyNutrientPlan(userID, dailyCalories, fatInGms, "
			+ " fatInCalories, carbsInGms, carbsInCalories, proteinInGms, " + "proteinInCalories, planDate)"
			+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String GET_DAILY_NUTRITION_PLAN = "select dailyCalories, fatInGms, fatInCalories, carbsInGms, "
			+ "carbsInCalories, proteinInGms, proteinInCalories" + " from UserDailyNutrientPlan";
	public static final String GET_USER_PRINCIPAL = "select userID, firstName, lastName, emailID, age, height, weight FROM UserInfo where emailID = ?";
	public static final String INS_USER_INFO = "insert into UserInfo(firstName, lastName, emailID, contactNumber, age, gender, height,"
			+ " weight, activityLevel, currentLocation)"
			+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String INS_USER_TASTE_PREFERENCES = "insert into UserRating (userID,foodID,rating,ratingTimeStamp) values (?,?,?,?)";

	public static final String GET_RECOMMENDATION_FOR_USER = "SELECT FoodInfo.name, FoodSimilarity.foodB, FoodSimilarity.similarity, FoodSimilarity.foodBCalories FROM FoodInfo,FoodSimilarity" +
			" where foodA = ? and foodBCalories < ? and FoodInfo.foodId = FoodSimilarity.foodB order by similarity desc limit ?";
	public static final String GET_MOST_PREFERRED_FOOD_ID = "SELECT foodID from UserRating where userID = ? order by ratingTimeStamp desc, rating desc limit 1";
	public static final String INS_USER_RATINGS = "insert into UserRating(userID, foodID, rating)"
													+ " values(?, ?, ?)";
}
