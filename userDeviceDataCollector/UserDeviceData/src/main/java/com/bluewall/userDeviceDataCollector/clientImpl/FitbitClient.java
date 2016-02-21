package com.bluewall.userDeviceDataCollector.clientImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import com.bluewall.userDeviceDataCollector.Client.Device;
import com.bluewall.userDeviceDataCollector.bean.UserConnectedDevice;
import com.bluewall.userDeviceDataCollector.common.Constants;

public class FitbitClient implements Device {

	// content type
	public UserConnectedDevice getRefreshedAccessToken(String oldRefreshToken) {
		String response;
		StringBuffer jsonResponse = new StringBuffer();
		String refreshToken, accessToken = null;

		URL url;
		try {
			url = new URL(Constants.FITBIT_REFRESH_TOKEN_URL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod(Constants.POST_METHOD);

			conn.setRequestProperty(Constants.AUTHORIZATION, getEncodedAuthorization());
			conn.setRequestProperty(Constants.CONTENT_TYPE, Constants.WWW_FORM_URL_ENCODED);
			conn.setDoOutput(true);

			DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
			ds.writeBytes(Constants.GRANT_TYPE + oldRefreshToken);
			ds.flush();
			ds.close();

			int responseCode = conn.getResponseCode();

			if (responseCode != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((response = br.readLine()) != null) {
					jsonResponse.append(response);
				}
				br.close();
				conn.disconnect();

				JSONObject obj = new JSONObject(jsonResponse.toString());
				
				refreshToken = (String) obj.get(Constants.REFRESH_TOKEN);
				accessToken = (String) obj.getString(Constants.ACCESS_TOKEN);
				
				UserConnectedDevice userDevice = new UserConnectedDevice();
				userDevice.setRefreshToken(refreshToken);
				userDevice.setAccessToken(accessToken);
				return userDevice;
			}
		} catch (IOException io) {
			System.out.println("An IO Exception has occured");
		}
		return null;
	}

	private String getEncodedAuthorization() {
		String encodedAuthString = null;
		try {
			encodedAuthString = Constants.BASIC + " "
					+ Base64.encodeBase64(Constants.FITBIT_APP_CLIENT_ID_CLIENT_SECRET.getBytes(Constants.UTF8));
		} catch (UnsupportedEncodingException use) {
			System.out.println("An UnsupportedEncodingException has occurred");
		}
		return encodedAuthString;
	}

	public String getAccessToken(String refreshToken, String deviceUserId) {

		return null;
	}

	public String getUserActivityInfo(String date, String date2, String accessToken) {

		StringBuffer sb = new StringBuffer();
		URL url;
		try {

			String acitivtyURLWithParams = Constants.FITBIT_ACTIVITY_API + date + ".json";

			url = new URL(acitivtyURLWithParams);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod(Constants.GET_MEHTOD);
			conn.setRequestProperty(Constants.AUTHORIZATION, accessToken);
			conn.setRequestProperty(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			in.close();
		} catch (IOException e) {

			System.out.println("An IO Excpetion has occurred");
		}
		return sb.toString();

	}

	
}
