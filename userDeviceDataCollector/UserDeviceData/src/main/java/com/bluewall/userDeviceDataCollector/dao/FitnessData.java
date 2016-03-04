package com.bluewall.userDeviceDataCollector.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.extern.slf4j.Slf4j;

import org.bson.Document;

import com.bluewall.userDeviceDataCollector.common.Constants;
import com.bluewall.userDeviceDataCollector.common.Queries;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

/**
 * @author Jenil
 *
 */

@Slf4j
public class FitnessData {

	MongoCollection<Document> collection;
	MongoDatabase db;
	
	/**
	 * @param Fitness Api data
	 * @param userID
	 * @param deviceType
	 */
	public void insertDeviceData(String data, int userID, String deviceType){
		
		MongoDBConnections dbconn = new MongoDBConnections();
		MongoClient mongo = dbconn.returnMongoConnection();
		db = mongo.getDatabase(Constants.USER_ACTIVITY_DB_NAME);
		collection = db.getCollection(Constants.USER_ACTIVITY_COLLECTION_NAME);
		log.info("Fetching Mongo DB details");
		
		Document document = new Document();
		
		if(collection.find().iterator().hasNext()){
			SqlDBConnections sqldbconn = new SqlDBConnections(
					Constants.MYSQL_CONN_URL,
					Constants.USER_DB_NAME,
					Constants.USERNAME,
					Constants.PASSWORD);
			Connection connection = sqldbconn.returnSQLConnection();
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(Queries.GET_ACTIVITY_LOG_DETAILS);
				log.debug("Fetched last Activity Log ID in DB");
				
				while (resultSet.next()) {
					int activityLogID = resultSet.getInt("activityLogID");
					document.put("doc_id", activityLogID+1);
					log.debug("Inserted last record ID in document");
				}
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("SQL Exception occured while fetching user details");
			}
		}
		else{
			document.put("doc_id", 1);
			log.debug("Inserted first document record");
		}
		document.put("user_id", userID);
	    document.put("device_type", deviceType);
	    collection.insertOne(document);
	    log.debug("Document Inserted");
	    
	    DBObject dbObject = (DBObject)JSON.parse(data);
	    BasicDBObject newDocument = new BasicDBObject();
		newDocument.putAll(dbObject);
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
		collection.updateOne(document, updateObj);
		log.info("API Data added in collection");
	}
}
