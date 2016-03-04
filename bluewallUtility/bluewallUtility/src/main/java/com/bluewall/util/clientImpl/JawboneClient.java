package com.bluewall.util.clientImpl;

import com.bluewall.util.bean.UserConnectedDevice;
import com.bluewall.util.client.ClientInterface;
import com.bluewall.util.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.JSONObject;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j

/**
 * This class contains methods which make calls to several Jawbone APIs to fetch
 * access token, refresh token , activity data, etc
 *
 */
public class JawboneClient implements ClientInterface {

    private HttpAuthenticationFeature basicAuthentication =
            HttpAuthenticationFeature.basic(Constants.JAWBONE_APP_CLIENT_ID, Constants.JAWBONE_APP_CLIENT_SECRET);

    private WebTarget jawboneTarget = ClientBuilder.newClient()
            .register(basicAuthentication)
            .target(Constants.JAWBONE_BASE_URL);

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
            // TODO remove DB activity
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

    @Override
    public String getAccessToken(String userId) {
        return null;
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

}
