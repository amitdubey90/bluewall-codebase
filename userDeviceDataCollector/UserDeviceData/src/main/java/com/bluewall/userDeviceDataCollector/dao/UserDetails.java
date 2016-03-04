package com.bluewall.userDeviceDataCollector.dao;

import com.bluewall.userDeviceDataCollector.common.Constants;
import com.bluewall.userDeviceDataCollector.common.Queries;
import com.bluewall.util.bean.UserConnectedDevice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jenil
 *
 */

@Slf4j
public class UserDetails {

	/**
	 * @return User ID and Device ID of User Connected to Device
	 */
	public List<UserConnectedDevice> getUserDetails(){
		
		List<UserConnectedDevice> userConnectedDeviceList = null;
		log.info("Fetching SQL Db Connection");
		SqlDBConnections dbconn = new SqlDBConnections(
				Constants.MYSQL_CONN_URL,
				Constants.USER_DB_NAME,
				Constants.USERNAME,
				Constants.PASSWORD);
		
		Connection connection = dbconn.returnSQLConnection();
		try {
			log.info("Fetching User ID and Device ID of all users");
			userConnectedDeviceList = new ArrayList<UserConnectedDevice>();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(Queries.GET_USER_DETAILS);

			while (resultSet.next()) {
				UserConnectedDevice userConnectedDevice = new UserConnectedDevice();
				userConnectedDevice.setUserID(resultSet.getInt("userID"));
				userConnectedDevice.setDeviceID(resultSet.getInt("deviceID"));
				userConnectedDeviceList.add(userConnectedDevice);
			}
			connection.close();
		} catch (SQLException e) {
			log.error("SQL Exception occured while fetching user details");
		}
		return userConnectedDeviceList;
	}
}
