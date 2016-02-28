package com.bluewall.feservices.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlDBConnections {

	String dbUrl;
	String dbClass;
	String username;
	String password;
	String dbName;
	Connection conn;
	
	public SqlDBConnections(String dbConnURL, String dbName, String username, String password) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		/* SQL Connection */
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try {
			conn = DriverManager.getConnection(dbConnURL + "/" + dbName, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection returnSQLConnection() {
		return conn;
	}
}
