package com.bluewall.util.clientImpl;

import com.bluewall.util.bean.UserConnectedDevice;
import com.bluewall.util.client.ClientInterface;
import com.bluewall.util.common.Constants;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.BasicAuthentication;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 * This class contains methods which make calls to several fitbit APIs to fetch
 * access token, refresh token , activity data, etc
 */
@Slf4j
public class FitbitClient implements ClientInterface {


    private HttpAuthenticationFeature basicAuthentication =
            HttpAuthenticationFeature.basic(Constants.FITBIT_APP_ID, Constants.FITBIT_APP_CLIENT_SECRET);

    private WebTarget fitbitTarget = ClientBuilder.newClient()
            .register(basicAuthentication)
            .target(Constants.FITBIT_BASE_URL);

    /**
     * This method fetches a new access token for a user based on the refresh
     * token. The new access token along with the new refresh token is then
     * stored in the database.
     */
    public UserConnectedDevice getRefreshedAccessToken(Connection dbconn, String oldRefreshToken, int userID) {
        UserConnectedDevice userDevice = new UserConnectedDevice();
        String refreshToken, accessToken = null;
        Statement stmt = null;
        try {

            Form form = new Form();
            form.param("grant_type", "refresh_token");
            form.param("refresh_token", oldRefreshToken);

            String response = fitbitTarget.path(Constants.FITBIT_REFRESH_TOKEN_PATH)
                    .request()
                    .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);


            JSONObject obj = new JSONObject(response);
            log.info("Fetching Refresh Token for Fitbit user");
            refreshToken = (String) obj.get(Constants.REFRESH_TOKEN_KEY);
            log.debug("Refresh token fetched {}", refreshToken);
            log.info("Fetching Access Token for Fitbit user");
            accessToken = obj.getString(Constants.ACCESS_TOKEN_KEY);
            log.debug("Access token fetched {}", accessToken);
            userDevice.setRefreshToken(refreshToken);
            userDevice.setAccessToken(accessToken);
            /*TODO remove db activity from here.
             Move it to token handler. Also, the update needs to consider device type as well*/
            stmt = dbconn.createStatement();
            String updateTokens = "UPDATE UserConnectedDevice SET refreshToken = " + refreshToken
                    + ",accessToken = " + accessToken + " where userID = " + userID;
            stmt.executeUpdate(updateTokens);
            log.info("Refresh Token, Access token for fitbit user updated", updateTokens);

        } catch (SQLException sqlExp) {
            log.error("A SQL Exception has occured {}", sqlExp);
        } catch (Exception e) {
            log.error("An  Exception has occured {}", e);
        } finally {
            if (dbconn != null)
                try {
                    dbconn.close();
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException sExp) {
                    log.error("SQL Excpetion in finally block {}", sExp);
                }

        }
        return userDevice;
    }

    /**
     * This method makes a call to Fitbit API which fetches User Activity
     * Information tracked through the device
     */
    public String getUserActivityInfo(String date, String date2, String accessToken) {
        // TODO check why date2 is not used
        try {
            String response = fitbitTarget.path(Constants.FITBIT_ACTIVITY_API_PATH)
                    .path(date + ".json")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", "Bearer " + accessToken)
                    .get(String.class);
            return response;
        } catch (Exception e) {
            log.error("An Exception has occurred in getUserActivityInfo, {}", e);
        }
        return null;
    }


    @Override
    public String getAuthorizationRequestUrl(String userId, String redirectUri) {
        return new AuthorizationRequestUrl(Constants.FITBIT_OAUTH_CODE_URL, Constants.FITBIT_APP_ID,
                Arrays.asList("code"))
                .set("scope", String.join(" ", Constants.FITBIT_SCOPES))
                .setState(userId)
                .setRedirectUri(redirectUri).build();
    }

    @Override
    public TokenResponse getAccessToken(String code, String redirectUri) throws IOException {

        AuthorizationCodeTokenRequest request = new AuthorizationCodeTokenRequest(
                new NetHttpTransport(), new JacksonFactory(),
                new GenericUrl(Constants.FITBIT_OAUTH_TOKEN_URL), code)
                .setRedirectUri(redirectUri)
                .setClientAuthentication(
                        new BasicAuthentication(Constants.FITBIT_APP_ID, Constants.FITBIT_APP_CLIENT_SECRET));

        return request.execute();
    }

}

