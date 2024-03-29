package com.bluewall.feservices.service;

import java.util.List;

import com.bluewall.util.bean.UserActivityLog;

public interface ActivityService {

	/**
	 * @param userID
	 * @return List of UserActivityLog objects
	 */
	public List<UserActivityLog> getUserActivityLogs(int userID);

	/*
	 * @param userID
	 * 
	 * @param UserActivityLog bean object
	 */
	public void createActivity(UserActivityLog userActivity, int userId);

}