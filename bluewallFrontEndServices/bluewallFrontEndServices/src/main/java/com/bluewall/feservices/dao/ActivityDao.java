package com.bluewall.feservices.dao;

import java.util.List;

import com.bluewall.util.bean.UserActivityLog;
import com.bluewall.util.bean.UserCredential;

public interface ActivityDao {
	
	/**
	 * @param userID
	 * @return List of UserActivityLog objects
	 */
	public List<UserActivityLog> getUserActivityLogs(int userID);

	public UserCredential getUserDeviceInfo(int userId);
	
	/*
	 * @param userId
	 * @param UserActivityLog bean object
	 */
	public void createActivity(UserActivityLog userActivity, int userId);
	
}
