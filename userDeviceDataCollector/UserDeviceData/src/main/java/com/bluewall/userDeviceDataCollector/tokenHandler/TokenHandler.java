package com.bluewall.userDeviceDataCollector.tokenHandler;

import com.bluewall.userDeviceDataCollector.common.Constants;
import com.bluewall.userDeviceDataCollector.dao.SqlDBConnections;
import com.bluewall.util.bean.UserConnectedDevice;
import com.bluewall.util.client.ClientInterface;
import com.bluewall.util.common.DeviceType;
import com.bluewall.util.factory.DeviceClientFactory;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;


@Slf4j

public class TokenHandler {

	DeviceClientFactory devFac = new DeviceClientFactory();
	ClientInterface devClient = null;

	// Get access token after refreshing.
	//Initial method to be called.
	
	public String getAccessToken(int userID, int deviceID)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		
		String accessToken = null;
		Statement stmt = null;
		ResultSet rs = null;
		Timestamp creationTime = null;
		String refreshToken = null;
		
		SqlDBConnections dbconn = new SqlDBConnections(
				Constants.MYSQL_CONN_URL, Constants.USER_DB_NAME,
				Constants.USERNAME, Constants.PASSWORD);
		
		Connection conn = dbconn.returnSQLConnection();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select accessToken, refreshToken, creationTime"
									+ "from UserConnectedDevice"
									+ "where userID = " + userID);
			while (rs.next()){
				accessToken = rs.getString("accessToken");
				refreshToken = rs.getString("refreshToken");
				creationTime = rs.getTimestamp("creationTime");
			}
			
			// check for token expiry before refreshing the token
			if (checkTokenExpiry(creationTime)) {
				String old_refreshToken = refreshToken;

				// Device id 10 - Fitbit, 11 -Jawbone
				if (deviceID == 10) {
					devClient = DeviceClientFactory.getClientInstance(DeviceType.FITBIT);
				} else {
					devClient = DeviceClientFactory.getClientInstance(DeviceType.JAWBONE);
				}

				UserConnectedDevice userdevice = devClient.getRefreshedAccessToken(old_refreshToken,userID);
				
				String updateTokens = "UPDATE UserConnectedDevice SET refreshToken = " + userdevice.getRefreshToken()
	                    + ",accessToken = " + userdevice.getAccessToken() 
	                    + " where userID = " + userID + "and deviceID = "
	                    + userdevice.getDeviceID();
				
	            stmt.executeUpdate(updateTokens);
	            
	            log.info("Refresh Token: "+userdevice.getRefreshToken()+", Access token: "+userdevice.getAccessToken()+" for device "+ userdevice.getDeviceID() +" and user " +userID+ " updated");
	            
				return userdevice.getAccessToken();
			} 
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		log.info("Token did not expiry, hence it is not refreshed.");
		return accessToken;
	}


	// Module to check whether to refresh access token or not.
	@SuppressWarnings("deprecation")
	public boolean checkTokenExpiry(Timestamp creationTime) {
			Date date = new Date();
			long time = date.getTime();
			Timestamp ts = new Timestamp(time);

			if (ts.getDate() == creationTime.getDate()) {
				if ((ts.getHours() - creationTime.getHours()) > 1) {
					return true;
				}
			}
		return false;
	}

}
