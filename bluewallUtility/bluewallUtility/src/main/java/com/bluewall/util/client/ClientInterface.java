package com.bluewall.util.client;


import java.io.IOException;

import com.bluewall.util.bean.UserConnectedDevice;
import com.google.api.client.auth.oauth2.TokenResponse;

public interface ClientInterface {

    UserConnectedDevice getRefreshedAccessToken(String oldRefreshToken, int userID);

    String getAuthorizationRequestUrl(String userId, String accessToken);

    TokenResponse getAccessToken(String authCode, String accessToken) throws IOException;

    String getUserActivityInfo(String strOne, String strTwo, String strThree);

	String getRecentUserActivity(String accessToken);

}
