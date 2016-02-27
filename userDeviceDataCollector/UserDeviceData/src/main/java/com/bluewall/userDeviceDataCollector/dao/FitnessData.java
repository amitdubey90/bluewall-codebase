package com.bluewall.userDeviceDataCollector.dao;


import org.bson.Document;

import com.bluewall.userDeviceDataCollector.common.Constants;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class FitnessData {

	MongoCollection<Document> collection;
	MongoDatabase db;
	
	public void insertDeviceData(String data, int userID, String deviceType) throws Exception{
		MongoDBConnections dbconn = new MongoDBConnections();
		MongoClient mongo = dbconn.returnMongoConnection();
		db = mongo.getDatabase(Constants.USER_ACTIVITY_DB_NAME);
		collection = db.getCollection(Constants.USER_ACTIVITY_COLLECTION_NAME);
		Document document = new Document();
		document.put("user_id", userID);
	    document.put("device_type", deviceType);
	    collection.insertOne(document);
	    
	    /* Adding API Data to existing document in collection */
	    DBObject dbObject = (DBObject)JSON.parse(data);
	    BasicDBObject newDocument = new BasicDBObject();
		newDocument.putAll(dbObject);

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		collection.updateOne(document, updateObj);
	}
}
