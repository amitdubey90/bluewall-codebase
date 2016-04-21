package com.bluewall.util.bean;

import java.sql.Date;
import java.sql.Timestamp;

/*
 * POJO for user's food log.
 */

public class UserFood {
	
	private String name;
	private String type;
	private float calories;
	private float weightConsumed;
	private Date foodLogDate;
	private Timestamp logTime;
	
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

	public Date getFoodLogDate() {
		return foodLogDate;
	}

	public void setFoodLogDate(Date foodLogDate) {
		this.foodLogDate = foodLogDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getLogTime() {
		return logTime;
	}

	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}

	
	
} 
