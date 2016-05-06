package com.bluewall.foodDatabasebuilder.common;


public class Queries {

	public static final String GET_FOOD_ID = "SELECT foodId FROM FoodId where isInserted = 'false' limit 1200";
	public static final String INSERT_FOOD_ID = "Insert into FoodId (foodId, isInserted) value (?,?)";
	public static final String UPDATE_FOOD_ID = "UPDATE FoodId set isInserted = ? where foodId = ?";
	public static final String INSERT_FOOD_NUTRIENT_INFO = "Insert into FoodNutrientPerXGram (foodId,eqvGMS,water,energy,protein,fat,carbohydrates,sugar) value(?,?,?,?,?,?,?,?)";
}
