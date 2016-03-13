package com.bluewall.util.bean;

import java.sql.Timestamp;

/*
 * POJO class for fetching user activity logs.
 */

public class UserActivityLog {
	private int userID;
	private float distance;
	private Timestamp startTime;
	private int duration;
	private int caloriesBurnt;
	private String name;
	private String MET;
	private int activityID;
	private String type;
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
	public float getDistance() {
		return distance;
	}
	
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getCaloriesBurnt() {
		return caloriesBurnt;
	}
	
	public void setCaloriesBurnt(int caloriesBurnt) {
		this.caloriesBurnt = caloriesBurnt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMET() {
		return MET;
	}

	public void setMET(String mET) {
		MET = mET;
	}

	public int getActivityID() {
		return activityID;
	}

	public void setActivityID(int activityID) {
		this.activityID = activityID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
