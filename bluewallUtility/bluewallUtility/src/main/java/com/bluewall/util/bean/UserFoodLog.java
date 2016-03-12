package com.bluewall.util.bean;

import java.sql.Timestamp;

/*
 * POJO for fetching user's food log.
 */

public class UserFoodLog {
	
	private String name;
	private String type;
	private float calories;
	private float weightConsumed;
	private Timestamp timeConsumed;
	
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
	
} 
