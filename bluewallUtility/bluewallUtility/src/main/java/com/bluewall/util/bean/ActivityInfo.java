package com.bluewall.util.bean;

import com.bluewall.util.utility.GenericUtil;

public class ActivityInfo {

	private int activityId;
	private long calories;
	private int distance;
	private String description;
	private String duration;
	private String name;

	public int getActivityId() {
		return activityId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDistance() {
		return distance;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		Long millis = new Long(duration);
		this.duration = GenericUtil.getHoursAndMinutesFromMillisecs(millis);
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public long getCalories() {
		return calories;
	}

	public void setCalories(long calories) {
		this.calories = calories;
	}

}
