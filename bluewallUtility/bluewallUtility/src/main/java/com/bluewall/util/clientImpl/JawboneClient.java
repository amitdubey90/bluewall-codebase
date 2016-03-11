package com.bluewall.util.clientImpl;

import com.bluewall.util.bean.UserConnectedDevice;
import com.bluewall.util.client.ClientInterface;
import com.bluewall.util.common.Constants;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.JSONObject;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Arrays;

@Slf4j

/**
 * This class contains methods which make calls to several Jawbone APIs to fetch
 * access token, refresh token , activity data, etc
 *
 */
public class JawboneClient implements ClientInterface {

    private HttpAuthenticationFeature basicAuthentication =
            HttpAuthenticationFeature.basic(Constants.JAWBONE_APP_ID, Constants.JAWBONE_APP_CLIENT_SECRET);

    private WebTarget jawboneTarget = ClientBuilder.newClient()
            .register(basicAuthentication)
            .target(Constants.JAWBONE_BASE_URL);

    /**
     * This method fetches a new access token for a user based on the refresh
     * token. The new access token along with the new refresh token is then
     * stored in the database.
     */
    
    public UserConnectedDevice getRefreshedAccessToken(String oldRefreshToken, int userID) {
        UserConnectedDevice userDevice = UserConnectedDevice.builder().build();
        String refreshToken, accessToken = null;
      
        
            log.info("Fetching access token and refresh token for userID: {}", userID);

            Form form = new Form();
            form.param("grant_type", "refresh_token");
            form.param("refresh_token", oldRefreshToken);

            String response = jawboneTarget.path(Constants.JAWBONE_REFRESH_TOKEN_PATH)
                    .request()
                    .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);


            JSONObject obj = new JSONObject(response);

            log.info("Fetching Refresh Token for Jawbone user");
            refreshToken = (String) obj.get(Constants.REFRESH_TOKEN_KEY);
            log.debug("Refresh token fetched {}", refreshToken);
            log.info("Fetching Access Token for Jawbone user");
            accessToken = (String) obj.getString(Constants.ACCESS_TOKEN_KEY);
            log.debug("Access token fetched {}", accessToken);

            userDevice.setRefreshToken(refreshToken);
            userDevice.setAccessToken(accessToken);
            userDevice.setDeviceID(11);
            return userDevice;
    }

    @Override
    public String getAuthorizationRequestUrl(String userId, String redirectUri) {
        return new AuthorizationRequestUrl(Constants.JAWBONE_OAUTH_CODE_URL, Constants.JAWBONE_APP_ID,
                Arrays.asList("code"))
                .set("scope", String.join(" ", Constants.JAWBONE_SCOPES))
                .setState(userId)
                .setRedirectUri(redirectUri).build();
    }

    @Override
    public TokenResponse getAccessToken(String code, String redirectUri) throws IOException {
        AuthorizationCodeTokenRequest request = new AuthorizationCodeTokenRequest(
                new NetHttpTransport(), new JacksonFactory(),
                new GenericUrl(Constants.JAWBONE_OAUTH_TOKEN_URL), code)
                .setRedirectUri(redirectUri)
                .set("client_id", Constants.JAWBONE_APP_ID)
                .set("client_secret", Constants.JAWBONE_APP_CLIENT_SECRET);
        return request.execute();
    }

    /**
     * This method makes a call to Jawbone API which fetches User Activity
     * Information tracked through the device
     */
    public String getUserActivityInfo(String startTime, String endTime, String accessToken) {

        try {
            // TODO why long?
            Long sTime = new Long(startTime);
            Long eTime = new Long(endTime);

            String response = jawboneTarget.path(Constants.JAWBONE_ACTIVITY_PATH)
                    .queryParam("start_time", sTime)
                    .queryParam("end_time", eTime)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", "Bearer " + accessToken)
                    .get(String.class);
            log.info("Fetched user Activity Info: {}", response);
            return response;
        } catch (Exception e) {

            log.error("An exception has occurred in getUserActivityInfo, {}", e);
        }
        return null;
    }

	@Override
	public String getRecentUserActivity(String accessToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
