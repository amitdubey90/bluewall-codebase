package com.bluewall.feservices.util;

public class Queries {
	public static final String GET_USER_DETAILS = "Select userID, deviceID from UserConnectedDevice";
	public static final String GET_ACTIVITY_LOG_DETAILS = "select max(activityLogID) as activityLogID from userDatabase.ActivityLog";
	public static final String GET_TOTAL_CALORIE_BURNT = "select sum(caloriesBurnt) as caloriesBurnt from ActivityLog";
	public static final String GET_TOTAL_CALORIE_CONSUMED = "select sum(calories) as weightConsumed from FoodLog";
	public static final String GET_DAILY_CALORIES = "select dailyCalories from UserDailyNutrientPlan";
	public static final String INS_USER_ACTIVITY_LOG = "insert into ActivityLog(userID, name,distance,activityLogDate, "
			+ "duration, caloriesBurnt,loggedFrom, logTime)" + " values(?, ?, ?, ?, ?, ?, ? ,?)";
	public static final String INS_USER_FOOD_LOG = "insert into FoodLog(userID, type, foodID, weightConsumed, loggedFrom, calories, logTime, foodLogDate)"
			+ " values(?, ?, ?, ?, ?, ?, ?,?)";
	public static final String INS_USERS = "insert into users(username, password) values(?,?)";
	public static final String INS_USER_GOALS = "insert into UserGoal(userID, goalType, targetWeight,startDate, endDate) "
			+ "values(?, ?, ?, ?, ?)";
	public static final String GET_FOODID = "select foodId from FoodInfo";

	public static final String GET_USER_INFO = "select * from UserInfo";
	
	public static final String UPSERT_DAILY_NUTRITION_PLAN = "insert into UserDailyNutrientPlan(userID, dailyCalories, fatInGms, "
			+ "fatInCalories, carbsInGms, carbsInCalories, proteinInGms, proteinInCalories, planDate)"
			+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?)"
			+ " ON DUPLICATE KEY UPDATE"
			+ " dailyCalories = VALUES(dailyCalories),"
			+ "	fatInGms = VALUES(fatInGms),"
			+ " fatInCalories = VALUES(fatInCalories),"
			+ " carbsInGms = VALUES(carbsInGms),"
			+ " carbsInCalories = VALUES(carbsInCalories),"
			+ " proteinInGms = VALUES(proteinInGms),"
			+ " proteinInCalories = VALUES(proteinInCalories),"
			+ " planDate = values(planDate)";
	
	public static final String GET_DAILY_NUTRITION_PLAN = "select dailyCalories, fatInGms, fatInCalories, carbsInGms, "
			+ "carbsInCalories, proteinInGms, proteinInCalories" + " from UserDailyNutrientPlan";
	public static final String GET_USER_PRINCIPAL = "select userID, firstName, lastName, emailID, age, height, weight FROM UserInfo where emailID = ?";
	public static final String INS_USER_INFO = "insert into UserInfo(firstName, lastName, emailID, contactNumber, age, gender, height,"
			+ " weight, activityLevel, currentLocation)"
			+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String INS_USER_TASTE_PREFERENCES = "insert into UserRating (userID,foodID,rating,ratingTimeStamp) values (?,?,?,?)";

	public static final String GET_RECOMMENDATION_FOR_USER = "SELECT FoodInfo.name, FoodInfo.category, FoodInfo.imageUrl, FoodSimilarity.foodB, FoodSimilarity.similarity, FoodSimilarity.foodBCalories " +
			"FROM FoodInfo,FoodSimilarity where foodA = ?";
	public static final String GET_RECOMMENDATION_FOR_USER_CONDITION = " and foodBCalories < ? and FoodInfo.foodId = FoodSimilarity.foodB order by similarity desc limit ?";

	public static final String GET_PREFERRED_FOOD_ID = "(SELECT distinct(foodId) FROM UserRating WHERE userID = ? AND rating >= " +
			"(SELECT AVG(rating) FROM UserRating WHERE userId = ?) ORDER BY ratingTimeStamp DESC , rating DESC limit 3) union " +
			"(SELECT distinct(foodId) FROM userDatabase.FoodLog WHERE userID = ? ORDER BY logTime DESC LIMIT 2)";
	
	public static final String UPSERT_USER_RATINGS = "INSERT INTO UserRating (userID, foodID, rating) VALUES (?, ?, ?) "
														+ "ON DUPLICATE KEY UPDATE "
															+ "rating = VALUES(rating)";
	
	public static final String GET_USER_PROFILE = "select UserInfo.firstName,UserInfo.lastName, UserInfo.emailID, UserInfo.contactNumber, "
			+ "UserInfo.age, UserInfo.gender, UserInfo.weight, UserInfo.height, UserInfo.activityLevel, UserGoal.goalType, "
			+ "UserGoal.targetWeight, UserGoal.startDate, UserGoal.endDate from UserInfo, UserGoal";
	
	
	public static final String UPDATE_USER_PROFILE = "update UserInfo, UserGoal"
			+ " set UserInfo.height = ?, UserInfo.weight = ?, UserInfo.activityLevel = ?, UserGoal.targetWeight = ?, UserGoal.goalType = ?";

	public static final String GET_USER_NUTRIENTS_CONSUMED = "SELECT SUM(carbohydrates * weight / eqvGMS) AS carbs, SUM(fat * weight / eqvGMS) AS fat, " +
			"SUM(protein * weight / eqvGMS) AS protein FROM (SELECT foodId, carbohydrates, fat, protein, eqvGMS, " +
			"(SELECT weightConsumed FROM userDatabase.FoodLog WHERE foodId = f.foodId AND userId = ? AND foodLogDate = DATE(NOW()) 	limit 1) " +
			"weight FROM FoodNutrientPerXGram f WHERE foodId IN (SELECT foodId FROM FoodLog " +
			"WHERE userId = ? AND foodLogDate = DATE(NOW()))) AS t";
}
