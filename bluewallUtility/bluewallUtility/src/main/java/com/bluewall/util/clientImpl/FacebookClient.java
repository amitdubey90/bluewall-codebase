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

/**
 * This client contains methods which makes several calls to facebook api to
 * fetch authorization code, access token, user profile info, etc
 * 
 * @author rainashastri
 *
 */
public class FacebookClient implements SocialConnectionProvidersInterface {

	/**
	 * This method fetched long lived access otken (valid ) for 60 days for a
	 * particular user.
	 * 
	 * @param -code
	 * 
	 */
	@Override
	public UserCredential getNewAccessToken(String code) {

		WebTarget accessTokenReq = ClientBuilder.newClient().target(Constants.FACEBOOK_ACCESS_TOKEN)
				.queryParam("client_id", Constants.FACEBOOK_CLIENT_ID)
				.queryParam("client_secret", Constants.FACEBOOK_APP_SECRET).queryParam("code", code)
				.queryParam("redirect_uri", Constants.FACEBOOK_REDIRECT_URI);

		String response = accessTokenReq.request().get(String.class);
		JSONObject json = new JSONObject(response);
		String accessToken = json.getString("access_token");

		// fetch long lived access token
		WebTarget longLivedAccessTokenReq = ClientBuilder.newClient().target(Constants.FACEBOOK_ACCESS_TOKEN)
				.queryParam("client_id", Constants.FACEBOOK_CLIENT_ID)
				.queryParam("client_secret", Constants.FACEBOOK_APP_SECRET).queryParam("fb_exchange_token", accessToken)
				.queryParam("grant_type", "fb_exchange_token");

		UserCredential creds = new UserCredential();
		String longAccessTokenString = longLivedAccessTokenReq.request().get(String.class);
		JSONObject jsonObj = new JSONObject(longAccessTokenString);
		creds.setAccessToken(jsonObj.getString("access_token"));
		return creds;
	}

	/**
	 * This method fetches basic profile info for a user.
	 */
	@Override
	public UserProfile fetchUserProfile(UserCredential newUserCreds) {
		WebTarget profileInfo = ClientBuilder.newClient().target(Constants.FACEBOOK_USER_PROFILE)
				.queryParam("access_token", newUserCreds.getAccessToken())
				.queryParam("fields", Constants.FB_PROFILE_FIELDS);
		UserProfile profile = new UserProfile();

		String profileInfoString = profileInfo.request().get(String.class);
		JSONObject obj = new JSONObject(profileInfoString);

		if (obj.has("first_name")) {
			profile.setFirstName(obj.getString("first_name"));
		}
		if (obj.has("last_name")) {
			profile.setLastName(obj.getString("last_name"));
		}
		if (obj.has("gender")) {
			profile.setGender(obj.getString("gender"));
		}
		if (obj.has("email")) {
			profile.setEmailID(obj.getString("email"));
		}
		if (obj.has("location")) {
			profile.setCurrentLocation((new JSONObject(obj.get("location").toString()).getString("name")));
		}

		return profile;
	}

	/**
	 * Returns an authorization request url.
	 */
	@Override
	public String AuthorizationRequestUrl() {
		return new AuthorizationRequestUrl(Constants.FACEBOOK_DIALOG_OAUTH, Constants.FACEBOOK_CLIENT_ID,
				Arrays.asList("code")).set("scope", Constants.FACEBOOK_SCOPE)
						.setRedirectUri(Constants.FACEBOOK_REDIRECT_URI).build();
	}

}
