package com.bluewall.util.client;


import com.bluewall.util.bean.UserConnectedDevice;

import java.sql.Connection;

public interface ClientInterface {

    UserConnectedDevice getRefreshedAccessToken(Connection dbconn, String oldRefreshToken, int userID);

    String getAccessToken(String userId);

    String getUserActivityInfo(String strOne, String strTwo, String strThree);

}
