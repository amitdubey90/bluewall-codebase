package com.bluewall.userDeviceDataCollector.clientImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import com.bluewall.userDeviceDataCollector.bean.UserConnectedDevice;
import com.bluewall.userDeviceDataCollector.client.ClientInterface;
import com.bluewall.userDeviceDataCollector.common.Constants;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

/**
 * This class contains methods which make calls to several fitbit APIs to fetch
 * access token, refresh token , activity data, etc
 * 
 * @author rainashastri
 *
 */
@Slf4j
public class FitbitClient implements ClientInterface {

	/**
	 * This method fetches a new access token for a user based on the refresh
	 * token. The new access token along with the new refresh token is then
	 * stored in the database.
	 */
	public UserConnectedDevice getRefreshedAccessToken(Connection dbconn, String oldRefreshToken, int userID) {
		String response;
		UserConnectedDevice userDevice = new UserConnectedDevice();
		StringBuffer jsonResponse = new StringBuffer();
		String refreshToken, accessToken = null;
		Statement stmt = null;
		URL url;
		try {

			url = new URL(Constants.FITBIT_REFRESH_TOKEN_URL);
			log.info("Fitbit Refresh Token Url: {}", url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(Constants.POST_METHOD);
			conn.setRequestProperty(Constants.AUTHORIZATION, getEncodedAuthorization());
			conn.setRequestProperty(Constants.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED.toString());
			conn.setDoOutput(true);

			DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
			ds.writeBytes(Constants.GRANT_TYPE + oldRefreshToken);
			ds.flush();
			ds.close();

			int responseCode = conn.getResponseCode();

			if (responseCode != 200) {
				log.error(
						"Failed : HTTP error code : {} \n Error Stream {} \n Not able to fetch access Token and refresh token",
						conn.getResponseCode(), conn.getErrorStream());

			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((response = br.readLine()) != null) {
					jsonResponse.append(response);
				}
				br.close();
				conn.disconnect();
				JSONObject obj = new JSONObject(jsonResponse.toString());
				log.info("Fetching Refresh Token for Fitbit user");
				refreshToken = (String) obj.get(Constants.REFRESH_TOKEN_KEY);
				log.debug("Refresh token fetched {}", refreshToken);
				log.info("Fetching Access Token for Fitbit user");
				accessToken = (String) obj.getString(Constants.ACCESS_TOKEN_KEY);
				log.debug("Access token fetched {}", accessToken);
				userDevice.setRefreshToken(refreshToken);
				userDevice.setAccessToken(accessToken);
				stmt = dbconn.createStatement();
				String updateTokens = "UPDATE UserConnectedDevice SET refreshToken = " + refreshToken
						+ ",accessToken = " + accessToken + " where userID = " + userID;
				stmt.executeUpdate(updateTokens);
				log.info("Refresh Token, Access token forr fitbit user updated", updateTokens);
			}
		} catch (IOException ioExp) {

			log.error("An IO Exception has occured {}", ioExp);
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
	 * This method encodes the fitbit client Id and client secret using Base64
	 * encoder. This is the Authorization String in the request header
	 * 
	 * @return encodedAuthString
	 */
	private String getEncodedAuthorization() {
		String encodedAuthString = null;
		try {
			log.info("Fetching Encoded Authorization String");
			encodedAuthString = Constants.BASIC + " " + new String(
					Base64.encodeBase64(Constants.FITBIT_APP_CLIENT_ID_CLIENT_SECRET.getBytes(Constants.UTF8)));
		} catch (UnsupportedEncodingException use) {
			log.error("UnsupportedEncodingException has occured {}", use);
		}
		return encodedAuthString;
	}

	/**
	 * This method makes a call to Fitbit API which fetches User Activity
	 * Information tracked through the device
	 */
	public String getUserActivityInfo(String date, String date2, String accessToken) {

		StringBuffer sb = new StringBuffer();
		URL url;
		try {
			String acitivtyURLWithParams = Constants.FITBIT_ACTIVITY_API + date + ".json";
			url = new URL(acitivtyURLWithParams);
			log.info("Fitbit User Activity URL: {}", url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(Constants.GET_MEHTOD);
			conn.setRequestProperty(Constants.AUTHORIZATION, "Bearer " + accessToken);
			conn.setRequestProperty(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			log.error("An IOException has occured, {}", e);
		}
		log.info("Fetched user Activity Info: {}", sb.toString());
		return sb.toString();

	}

}
