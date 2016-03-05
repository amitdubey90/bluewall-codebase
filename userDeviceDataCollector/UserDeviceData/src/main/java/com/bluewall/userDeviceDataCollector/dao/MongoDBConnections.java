package com.bluewall.userDeviceDataCollector.dao;

import java.sql.Connection;

import lombok.extern.slf4j.Slf4j;

import com.bluewall.userDeviceDataCollector.common.Constants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * @author Jenil
 *
 */

@Slf4j
public class MongoDBConnections {

	Connection conn;
	MongoClient mongo;

	public MongoDBConnections() {

		log.info("Establishing Mongo Connection");
		MongoClientURI uri = new MongoClientURI(Constants.MONGO_CONN_URL);
		mongo = new MongoClient(uri);
	}

	/**
	 * @return Mongo Client Object
	 */
	public MongoClient returnMongoConnection() {
		return mongo;
	}
}
