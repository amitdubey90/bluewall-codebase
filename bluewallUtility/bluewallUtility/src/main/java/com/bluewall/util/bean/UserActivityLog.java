package com.bluewall.util.bean;

import java.sql.Date;

/*
 * POJO class for fetching user activity logs.
 */

public class UserActivityLog {

	private String type;
	private String name;
	private float distance;
	private float caloriesBurnt;
	private Date activityLogDate;
//	private Timestamp starTime;
//	private Timestamp endTime;

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getCaloriesBurnt() {
		return caloriesBurnt;
	}

	public void setCaloriesBurnt(float caloriesBurnt) {
		this.caloriesBurnt = caloriesBurnt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getActivityLogDate() {
		return activityLogDate;
	}

	public void setActivityLogDate(Date activityLogDate) {
		this.activityLogDate = activityLogDate;
	}
	
	

//	public Timestamp getStarTime() {
//		return starTime;
//	}
//
//	public void setStarTime(Timestamp starTime) {
//		this.starTime = starTime;
//	}
//
//	public Timestamp getEndTime() {
//		return endTime;
//	}
//
//	public void setEndTime(Timestamp endTime) {
//		this.endTime = endTime;
//	}

}
