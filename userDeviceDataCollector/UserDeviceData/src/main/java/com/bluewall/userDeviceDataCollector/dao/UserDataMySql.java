package com.bluewall.userDeviceDataCollector.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bluewall.userDeviceDataCollector.bean.UserConnectedDevice;
import com.bluewall.userDeviceDataCollector.common.Constants;
import com.bluewall.userDeviceDataCollector.common.Queries;

public class UserDataMySql {

	String dbUrl;
	String dbClass;
	String username;
	String password;

	public void getConnection() {
		dbUrl = "jdbc:mysql://user-db-instance.cqqcnirpnrkg.us-west-1.rds.amazonaws.com:3306/userDatabase";
		dbClass = Constants.DRIVER_CLASS;
		username = Constants.USERNAME;
		password = Constants.PASSWORD;
	}

	public List<UserConnectedDevice> getUserID() {
		List<UserConnectedDevice> userConnectedDeviceList = null;
		getConnection();
		try {
			Class.forName(dbClass);
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(Queries.GET_USER_ID);
			userConnectedDeviceList = new ArrayList<UserConnectedDevice>();

			while (resultSet.next()) {
				UserConnectedDevice userConnectedDevice = new UserConnectedDevice();
				userConnectedDevice.setUserID(resultSet.getInt("userID"));
				userConnectedDeviceList.add(userConnectedDevice);
			}

			connection.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userConnectedDeviceList;
	}
}
