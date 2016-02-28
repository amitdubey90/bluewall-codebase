package com.bluewall.userDeviceDataCollector.tokenHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import com.bluewall.userDeviceDataCollector.bean.UserConnectedDevice;
import com.bluewall.userDeviceDataCollector.client.Device;
import com.bluewall.userDeviceDataCollector.common.Constants;
import com.bluewall.userDeviceDataCollector.dao.SqlDBConnections;
import com.bluewall.userDeviceDataCollector.factory.DeviceFactory;

public class TokenHandler {

	DeviceFactory devFac = new DeviceFactory();
	Device devClient = null;

	// Get access token after refreshing.
	public String getAccessToken(int userID, int deviceID)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		
		String accessToken = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		SqlDBConnections dbconn = new SqlDBConnections(
				Constants.MYSQL_CONN_URL, Constants.USER_DB_NAME,
				Constants.USERNAME, Constants.PASSWORD);
		
		Connection conn = dbconn.returnSQLConnection();
		try {
			// check for token expiry before refreshing the token
			if (checkTokenExpiry(conn, userID)) {
				String old_refreshToken = getRefreshToken(conn, userID);

				// Device id 10 - Fitbit, 11 -Jawbone
				if (deviceID == 10) {
					devClient = devFac.getClientInstance("Fitbit");
				} else {
					devClient = devFac.getClientInstance("Jawbone");
				}

				UserConnectedDevice userdevice = devClient.getRefreshedAccessToken(conn, old_refreshToken, userID);
				return userdevice.getAccessToken();
			} else {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select accessToken from UserConnectedDevice where userID = " + userID);
				while (rs.next()) {
					try {
						accessToken = rs.getString("accessToken");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
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

		return accessToken;
	}

	// Fetch refresh token from database to refresh access token.
	public String getRefreshToken(Connection conn, int userID) {
		Statement stmt = null;
		ResultSet rs = null;
		String refreshToken = null;

		try {
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select refreshToken from UserConnectedDevice where userID = "
							+ userID);
			;

			while (rs.next()) {
				refreshToken = rs.getString("refreshToken");
			}

		} catch (SQLException e) {
			e.printStackTrace();
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

		return refreshToken;
	}

	// Module to check whether to refresh access token or not.
	@SuppressWarnings("deprecation")
	public boolean checkTokenExpiry(Connection conn, int userID) {
		ResultSet rs = null;
		Statement stmt = null;
		Timestamp creationTime = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select creationTime from UserConnectedDevice where userID = " + userID);
			while (rs.next()) {
				creationTime = rs.getTimestamp("creationTime");
			}

			Date date = new Date();
			long time = date.getTime();
			Timestamp ts = new Timestamp(time);

			if (ts.getDate() == creationTime.getDate()) {
				if ((ts.getHours() - creationTime.getHours()) > 1) {
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		return false;
	}

}
