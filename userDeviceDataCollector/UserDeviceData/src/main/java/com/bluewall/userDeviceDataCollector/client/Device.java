package com.bluewall.userDeviceDataCollector.client;

import com.bluewall.userDeviceDataCollector.bean.UserConnectedDevice;

public interface Device {
	
	public UserConnectedDevice getRefreshedAccessToken(String oldRefreshToken);
	//public String getAccessToken(String userId);
	public String getUserActivityInfo(String strOne, String strTwo, String strThree);

}
