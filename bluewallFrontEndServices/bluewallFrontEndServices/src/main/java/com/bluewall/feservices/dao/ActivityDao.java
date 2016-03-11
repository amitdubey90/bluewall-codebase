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
	
}
