package com.bluewall.userDeviceDataCollector.clientImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import com.bluewall.userDeviceDataCollector.bean.UserConnectedDevice;
import com.bluewall.userDeviceDataCollector.client.Device;
import com.bluewall.userDeviceDataCollector.common.Constants;

public class FitbitClient implements Device {

	// content type
	public UserConnectedDevice getRefreshedAccessToken(Connection dbconn,String oldRefreshToken, int userID) {
		String response;
		UserConnectedDevice userDevice = new UserConnectedDevice();
		StringBuffer jsonResponse = new StringBuffer();
		String refreshToken, accessToken = null;
		Statement stmt = null;
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
				conn.getErrorStream();
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			} 
			else {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((response = br.readLine()) != null) {
					jsonResponse.append(response);
				}
				br.close();
				conn.disconnect();

				JSONObject obj = new JSONObject(jsonResponse.toString());
				
				refreshToken = (String) obj.get(Constants.REFRESH_TOKEN);
				accessToken = (String) obj.getString(Constants.ACCESS_TOKEN);
				
				userDevice.setRefreshToken(refreshToken);
				userDevice.setAccessToken(accessToken);
				
				stmt = dbconn.createStatement();
				String updateTokens = "UPDATE UserConnectedDevice SET refreshToken = " + refreshToken +",accessToken = " + accessToken + " where userID = "+userID;
			    stmt.executeUpdate(updateTokens);
			}
		} 
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return userDevice;
	}

	private String getEncodedAuthorization() {
		String encodedAuthString = null;
		try {
			encodedAuthString = Constants.BASIC + " "
					+ new String(Base64.encodeBase64(Constants.FITBIT_APP_CLIENT_ID_CLIENT_SECRET.getBytes(Constants.UTF8)));
		} catch (UnsupportedEncodingException use) {
			System.out.println("An UnsupportedEncodingException has occurred");
		}
		return encodedAuthString;
	}

	public String getUserActivityInfo(String date, String date2, String accessToken) {

		StringBuffer sb = new StringBuffer();
		URL url;
		try {

			String acitivtyURLWithParams = Constants.FITBIT_ACTIVITY_API + date + ".json";

			url = new URL(acitivtyURLWithParams);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod(Constants.GET_MEHTOD);
			conn.setRequestProperty(Constants.AUTHORIZATION, "Bearer "+accessToken);
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
	
	public static void main(String args[]){
		FitbitClient client = new FitbitClient();
		//client.getRefreshedAccessToken("b57a634ca1cda8cc4aea728eb63fa620d3042dc9f418bd4b9206d2ec71e66810");
		client.getUserActivityInfo("today", null, "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NTYwNDU5NjksInNjb3BlcyI6Indsb2Mgd3BybyB3bnV0IHdzbGUgd3NldCB3d2VpIHdociB3YWN0IHdzb2MiLCJzdWIiOiIzTlRMTUoiLCJhdWQiOiIyMjdHQzUiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJpYXQiOjE0NTYwNDIzNjl9.3isRT0f6rtl9gSchAbqu2KPz-oavmH6zM7MpZKJGshY");
	}
	
}
