package com.bluewall.util.client;

import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.bean.UserProfile;

public interface SocialConnectionProvidersInterface {

	String AuthorizationRequestUrl();

	UserCredential getNewAccessToken(String code);

	UserProfile fetchUserProfile(UserCredential newUserCreds);

}
