package com.bluewall.userDeviceDataCollector.Client;

import com.bluewall.userDeviceDataCollector.bean.UserConnectedDevice;

public interface Device {
	
	public UserConnectedDevice getRefreshedAccessToken(String oldRefreshToken);
	public String getAccessToken(String refreshToken,String userId);
	public String getUserActivityInfo(String strOne, String strTwo, String strThree);

}
