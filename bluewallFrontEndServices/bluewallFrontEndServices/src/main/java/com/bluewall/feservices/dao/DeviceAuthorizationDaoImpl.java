package com.bluewall.feservices.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import com.bluewall.feservices.util.DatabaseResouceCloser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bluewall.feservices.util.SQLQueries;
import com.bluewall.util.bean.AccessCredentials;
import com.bluewall.util.bean.UserConnectedDevice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DeviceAuthorizationDaoImpl implements DeviceAuthorizationDaoIfc {

	@Autowired
	DataSource dataSource;

	public boolean storeUserAccessToken(AccessCredentials credentials) {
		log.info("storeUserAccessToken started");

		Connection connection = null;
		PreparedStatement pst = null;
		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(SQLQueries.INSERT_USER_ACCESS_TOKEN);
			int colId = 1;

			pst.setInt(colId++, credentials.getUserId());
			pst.setInt(colId++, credentials.getDeviceId());
			pst.setString(colId++, credentials.getAccessToken());
			pst.setString(colId++, credentials.getRefreshToken());
			pst.setTimestamp(colId++, credentials.getExpirationTime());
			pst.setTimestamp(colId++, new java.sql.Timestamp(new java.util.Date().getTime()));
			pst.execute();
			log.info("storeUserAccessToken successful");
			return true;
		} catch (SQLException e) {
			log.error("SqlException in storeUserAccessToken {}", e);
		} finally {
			DatabaseResouceCloser.closeAllSilent(connection, pst, null);
		}
		return false;
	}

	@Override

	public UserConnectedDevice checkIfUserHasDevice(int userID) {
		UserConnectedDevice device = UserConnectedDevice.builder().build();
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			log.info("Checking if user has any devices connected");
			connection =  dataSource.getConnection();
			pst = connection.prepareStatement(
					"SELECT d.deviceID, d.deviceConnectionTime, a.logTime FROM UserConnectedDevice d LEFT JOIN " +
							"ActivityLog a ON (d.userId = a.userID AND d.deviceID = a.loggedFrom AND a.loggedFrom != ?) " +
							"WHERE d.userId = ? ORDER BY a.logTime DESC");
			pst.setInt(1, 14);
			pst.setInt(2, userID);
			rs = pst.executeQuery();
			if (rs.next()) {
				device.setDeviceID(rs.getInt("deviceID"));
				device.setDeviceConnectionTime(rs.getTimestamp("deviceConnectionTime"));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				if(null==(rs.getString("logTime"))){
					device.setExpirationTime(null);
				}else{
					Date parsedDate = dateFormat.parse(rs.getString("logTime"));
					Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
					device.setExpirationTime(timestamp);
				}
				
				log.info("User has device connected {} ", device.getDeviceID());
			}
		} catch (SQLException e) {
			log.error("SqlException in checkIfUserHasDevice {}", e);
		} catch (ParseException e) {
			log.error("ParseException in checkIfUserHasDevice {}", e);
		} finally {
			DatabaseResouceCloser.closeAllSilent(connection, pst, rs);
		}
		return device;
	}
}
