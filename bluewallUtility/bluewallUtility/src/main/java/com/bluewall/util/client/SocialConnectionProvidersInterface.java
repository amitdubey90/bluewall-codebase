package com.bluewall.util.client;

import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.bean.UserProfile;

public interface SocialConnectionProvidersInterface {

	UserCredential getNewAccessToken(String accessToken, String refreshToken);

	UserCredential authorizeApp(String redirectUri, String facebookScopes);

	UserProfile fetchUserProfile(UserCredential newUserCreds);

	UserCredential getAccessAndRefreshToken(String code);

}
