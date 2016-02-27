package com.bluewall.userDeviceDataCollector.dao;

import java.sql.Connection;
import com.bluewall.userDeviceDataCollector.common.Constants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDBConnections {

	Connection conn;
	MongoClient mongo;

	public MongoDBConnections() {

		/* Mongo Connection */
		MongoClientURI uri = new MongoClientURI(Constants.MONGO_CONN_URL);
		mongo = new MongoClient(uri);
	}

	public MongoClient returnMongoConnection() {
		return mongo;
	}
}
