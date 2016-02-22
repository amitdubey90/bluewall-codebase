package com.bluewall.userDeviceDataCollector.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.bson.Document;
import com.bluewall.userDeviceDataCollector.common.Constants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseConnections {

	String dbUrl;
	String dbClass;
	String username;
	String password;
	MongoCollection<Document> collection;
	MongoDatabase db;

	//Establish connection to database.
	public Connection establishMYSQLConnection(String dbConnURL,String dbName, String uName, String passwd) {
		Connection conn = null;
		try {
			Class.forName(Constants.DRIVER_CLASS).newInstance();
			conn = DriverManager.getConnection(dbConnURL+"/"+dbName, uName, passwd);
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			System.out.println("Your MySQL JDBC driver is missing !!");
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public MongoCollection<Document> establishMONGOConnection() {
		try{
			/* Connecting to MongoDB */
			MongoClientURI uri = new MongoClientURI(Constants.MONGO_CONN_URL);
			MongoClient mongo = new MongoClient(uri);
			db = mongo.getDatabase(Constants.USER_ACTIVITY_DB_NAME);
			collection = db.getCollection(Constants.USER_ACTIVITY_COLLECTION_NAME);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return collection;
	}
}
