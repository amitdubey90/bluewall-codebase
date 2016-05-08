package com.bluewall.util.bean;

import java.sql.Date;

/*
 * POJO class for fetching user activity logs.
 */

public class UserActivityLog {

	private String name;
	//private float distance;
	private float caloriesBurnt;
	private Date activityLogDate;
	private float duration;
	//private Timestamp logTime;
	private String loggedFrom;

	public String getLoggedFrom() {
		return loggedFrom;
	}

	public void setLoggedFrom(String loggedFrom) {
		this.loggedFrom = loggedFrom;
	}

	/*public Timestamp getLogTime() {
		return logTime;
	}

	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}*/

//	public float getDistance() {
//		return distance;
//	}
//
//	public void setDistance(float distance) {
//		this.distance = distance;
//	}

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

	public Date getActivityLogDate() {
		return activityLogDate;
	}

	public void setActivityLogDate(Date activityLogDate) {
		this.activityLogDate = activityLogDate;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

}
