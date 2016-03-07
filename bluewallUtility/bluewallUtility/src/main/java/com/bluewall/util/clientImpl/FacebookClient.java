package com.bluewall.util.clientImpl;

import java.util.Arrays;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.json.JSONObject;

import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.bean.UserProfile;
import com.bluewall.util.client.SocialConnectionProvidersInterface;
import com.bluewall.util.common.Constants;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;

public class FacebookClient implements SocialConnectionProvidersInterface {

	@Override
	public UserCredential getNewAccessToken(String code) {

		WebTarget accessTokenReq = ClientBuilder.newClient().target(Constants.FACEBOOK_ACCESS_TOKEN)
				.queryParam("client_id", Constants.FACEBOOK_CLIENT_ID)
				.queryParam("client_secret", Constants.FACEBOOK_APP_SECRET).queryParam("code", code)
				.queryParam("redirect_uri", Constants.REDIRECT_URI);

		String response = accessTokenReq.request().get(String.class);
		JSONObject json = new JSONObject(response);
		String accessToken = json.getString("access_token");

		// fetch long lived access token
		WebTarget longLivedAccessTokenReq = ClientBuilder.newClient().target(Constants.FACEBOOK_ACCESS_TOKEN)
				.queryParam("client_id", Constants.FACEBOOK_CLIENT_ID)
				.queryParam("client_secret", Constants.FACEBOOK_APP_SECRET).queryParam("fb_exchange_token", accessToken)
				.queryParam("grant_type", "fb_exchange_token");

		UserCredential creds = new UserCredential();
		JSONObject jsonObj = new JSONObject(longLivedAccessTokenReq);
		creds.setAccessToken(jsonObj.getString("access_token"));
		creds.setExpirationTime(jsonObj.getString("expires_in"));
		return creds;
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

	@Override
	public String AuthorizationRequestUrl() {
		return new AuthorizationRequestUrl(Constants.FACEBOOK_DIALOG_OAUTH, Constants.FACEBOOK_CLIENT_ID,
				Arrays.asList("code")).set("scope", Constants.FACEBOOK_SCOPE).setRedirectUri(Constants.REDIRECT_URI)
						.build();
	}

}
