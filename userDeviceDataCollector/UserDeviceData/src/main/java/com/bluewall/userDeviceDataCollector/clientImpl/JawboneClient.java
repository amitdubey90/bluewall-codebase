package com.bluewall.userDeviceDataCollector.clientImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.bluewall.userDeviceDataCollector.Client.Device;
import com.bluewall.userDeviceDataCollector.bean.UserConnectedDevice;
import com.bluewall.userDeviceDataCollector.common.Constants;

public class JawboneClient implements Device {

	
	public UserConnectedDevice getRefreshedAccessToken(String oldRefreshToken) {

		String response;
		StringBuffer jsonResponse = new StringBuffer();
		String refreshToken, accessToken = null;

		URL url;
		try {
			String JAWBONE_ACCESS_TOKEN_URL_PARAMS = Constants.JAWBONE_REFRESH_TOKEN_URL + "?client_id="
					+ Constants.JAWBONE_CLIENT_ID + "&client_secret=" + Constants.JAWBONE_CLIENT_SECRET + "&"+Constants.GRANT_TYPE
					+ oldRefreshToken;
			url = new URL(JAWBONE_ACCESS_TOKEN_URL_PARAMS);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod(Constants.POST_METHOD);

			// conn.setRequestProperty(Constants.AUTHORIZATION,
			// getEncodedAuthorization());
			// conn.setRequestProperty(Constants.CONTENT_TYPE,
			// Constants.WWW_FORM_URL_ENCODED);
			conn.setDoOutput(true);

			// DataOutputStream ds = new
			// DataOutputStream(conn.getOutputStream());
			//
			// ds.writeBytes(JAWBONE_ACCESS_TOKEN_URL_PARAMS);
			// ds.flush();
			// ds.close();

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

//	private String getEncodedAuthorization() {
//		String encodedAuthString = null;
//		try {
//			encodedAuthString = Constants.BEARER + " "
//					+ Base64.encodeBase64(Constants.JAWBONE_APP_CLIENT_ID_CLIENT_SECRET.getBytes(Constants.UTF8));
//		} catch (UnsupportedEncodingException use) {
//			System.out.println("An UnsupportedEncodingException has occurred");
//		}
//		return encodedAuthString;
//	}

	public String getAccessToken(String refreshToken, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserActivityInfo(String startTime, String endTime, String accessToken) {

		StringBuffer sb = new StringBuffer();
		URL url;
		try {
			Long sTime = new Long(startTime);
			Long eTime = new Long(endTime);
			String acitivtyURLWithParams = Constants.JAWBONE_ACTIVITY_API + "?start_time=" + sTime + "&endTime="
					+ eTime;
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
