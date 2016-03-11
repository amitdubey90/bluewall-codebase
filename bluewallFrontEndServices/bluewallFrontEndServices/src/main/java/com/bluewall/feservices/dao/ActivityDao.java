package com.bluewall.feservices.dao;

import java.util.List;

import com.bluewall.util.bean.UserActivityLog;

public interface ActivityDao {
	
	/**
	 * @param userID
	 * @return List of UserActivityLog objects
	 */
	
	public List<UserActivityLog> getUserActivityLogs(int userID);
	
}
