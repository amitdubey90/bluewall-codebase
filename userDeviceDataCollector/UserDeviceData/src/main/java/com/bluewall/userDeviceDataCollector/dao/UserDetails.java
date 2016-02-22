package com.bluewall.userDeviceDataCollector.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bluewall.userDeviceDataCollector.bean.UserConnectedDevice;
import com.bluewall.userDeviceDataCollector.common.Constants;
import com.bluewall.userDeviceDataCollector.common.Queries;

public class UserDetails {

	public List<UserConnectedDevice> getUserDetails(){
		
		DatabaseConnections dbconn = new DatabaseConnections();
		List<UserConnectedDevice> userConnectedDeviceList = null;
		Connection connection = dbconn.establishMYSQLConnection(Constants.MYSQL_CONN_URL,Constants.USER_DB_NAME,Constants.USERNAME,Constants.PASSWORD);
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(Queries.GET_USER_DETAILS);
			userConnectedDeviceList = new ArrayList<UserConnectedDevice>();

			while (resultSet.next()) {
				UserConnectedDevice userConnectedDevice = new UserConnectedDevice();
				userConnectedDevice.setUserID(resultSet.getInt("userID"));
				userConnectedDevice.setDeviceID(resultSet.getInt("deviceID"));
				userConnectedDeviceList.add(userConnectedDevice);
			}

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userConnectedDeviceList;
		
	}
}
