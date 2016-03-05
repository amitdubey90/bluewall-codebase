package com.bluewall.userDeviceDataCollector.common;


public class Queries {
	public static final String GET_USER_DETAILS = "Select userID, deviceID from UserConnectedDevice";
	public static final String GET_ACTIVITY_LOG_DETAILS = "select max(activityLogID) as activityLogID from userDatabase.ActivityLog";
}
