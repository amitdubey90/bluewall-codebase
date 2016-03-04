package com.bluewall.userDeviceDataCollector.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;

import com.bluewall.userDeviceDataCollector.common.Constants;

/**
 * @author Jenil
 *
 */

@Slf4j
public class SqlDBConnections {

	String dbUrl;
	String dbClass;
	String username;
	String password;
	String dbName;
	Connection conn;
	
	/**
	 * @param dbConnURL
	 * @param dbName
	 * @param username
	 * @param password
	 */
	public SqlDBConnections(String dbConnURL, String dbName, String username, String password){

		
		log.info("Establishing SQL DB Connection");
		try {
			Class.forName(Constants.DRIVER_CLASS).newInstance();
			conn = DriverManager.getConnection(dbConnURL + "/" + dbName, username, password);
		} catch (InstantiationException e) {
			log.error("Instantiation Exception Occured while establising SQL connection");
		} catch (IllegalAccessException e) {
			log.error("Illegal Access Exception Occured while establising SQL connection");
		} catch (ClassNotFoundException e) {
			log.error("Class Not Found Exception Occured while establising SQL connection");
		} catch (SQLException e) {
			log.error("SQL Exception Occured while establising connection");
		}
	}

	
	/**
	 * @return Connection Object
	 */
	public Connection returnSQLConnection() {
		return conn;
	}
}
