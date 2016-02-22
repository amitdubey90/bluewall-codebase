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

public class DatabaseConnections {

	String dbUrl;
	String dbClass;
	String username;
	String password;

	//Establish connection to database.
	public static Connection establishMYSQLConnection(String dbConnURL,String dbName, String uName, String passwd) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(dbConnURL+"/"+dbName, uName, passwd);
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("Your MySQL JDBC driver is missing !!");
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public List<UserConnectedDevice> getUserID() {
		List<UserConnectedDevice> userConnectedDeviceList = null;
		Connection connection = establishMYSQLConnection(Constants.MYSQL_CONN_URL,Constants.USER_DB_NAME,Constants.USERNAME,Constants.PASSWORD);
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(Queries.GET_USER_ID);
			userConnectedDeviceList = new ArrayList<UserConnectedDevice>();

			while (resultSet.next()) {
				UserConnectedDevice userConnectedDevice = new UserConnectedDevice();
				userConnectedDevice.setUserID(resultSet.getInt("userID"));
				userConnectedDeviceList.add(userConnectedDevice);
			}

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userConnectedDeviceList;
	}
}
