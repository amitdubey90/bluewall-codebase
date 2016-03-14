package com.bluewall.util.bean;

import java.sql.Timestamp;

/*
 * POJO for user's food log.
 */

public class UserFood {
	
	private String name;
	private String category;
	private String manufacturer;
	private String type;
	private float calories;
	private float weightConsumed;
	private Timestamp timeConsumed;
	private Timestamp foodLogTime;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public float getCalories() {
		return calories;
	}
	
	public void setCalories(float calories) {
		this.calories = calories;
	}
	
	public float getWeightConsumed() {
		return weightConsumed;
	}
	
	public void setWeightConsumed(float weightConsumed) {
		this.weightConsumed = weightConsumed;
	}
	
	public Timestamp getTimeConsumed() {
		return timeConsumed;
	}
	
	public void setTimeConsumed(Timestamp timeConsumed) {
		this.timeConsumed = timeConsumed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Timestamp getFoodLogTime() {
		return foodLogTime;
	}

	public void setFoodLogTime(Timestamp foodLogTime) {
		this.foodLogTime = foodLogTime;
	}
	
} 
