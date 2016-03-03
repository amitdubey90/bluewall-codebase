package com.bluewall.util.clientImpl;

import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.bean.UserProfile;
import com.bluewall.util.client.SocialConnectionProvidersInterface;

public class GoogleClient implements SocialConnectionProvidersInterface {

	@Override
	public UserCredential getNewAccessToken(String accessToken, String refreshToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserCredential authorizeApp(String redirectUri, String facebookScopes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProfile fetchUserProfile(UserCredential newUserCreds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserCredential getAccessAndRefreshToken(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
