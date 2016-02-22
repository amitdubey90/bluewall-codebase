package com.bluewall.userDeviceDataCollector.client;

import java.sql.Connection;

import com.bluewall.userDeviceDataCollector.bean.UserConnectedDevice;

public interface Device {
	
	public UserConnectedDevice getRefreshedAccessToken(Connection dbconn, String oldRefreshToken, int userID);
	//public String getAccessToken(String userId);
	public String getUserActivityInfo(String strOne, String strTwo, String strThree);

}
