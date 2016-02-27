package com.bluewall.userDeviceDataCollector.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.bluewall.userDeviceDataCollector.common.Constants;

public class SqlDBConnections {

	String dbUrl;
	String dbClass;
	String username;
	String password;
	String dbName;
	Connection conn;
	
	public SqlDBConnections(String dbConnURL, String dbName, String username, String password) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		/* SQL Connection */
		Class.forName(Constants.DRIVER_CLASS).newInstance();
		conn = DriverManager.getConnection(dbConnURL + "/" + dbName, username, password);
	}

	public Connection returnSQLConnection() {
		return conn;
	}
}
