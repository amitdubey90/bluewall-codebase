package mongodb;


import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class FitnessDataMongoDB {

	DBCollection collection;
	DB db;
	public FitnessDataMongoDB(){
		try{
			/* Connecting to MongoDB */
			MongoClient mongo = new MongoClient("localhost", 27017);
			db = mongo.getDB("fitness_data");
			collection = db.getCollection("fitness_data");
		}
		catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void insert(String data, String deviceType) throws Exception{
		
		BasicDBObject document = new BasicDBObject();
		//document.put("_id", getNextSequence("userid")); // Generating Auto Incremented ID 
		
		/*BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("seq", 1);
		collection.remove(searchQuery);*/
		
	    document.put("device_type", deviceType);
	    collection.insert(document);
	    
	    /* Adding API Data to existing document in collection */
	    DBObject dbObject = (DBObject)JSON.parse(data);
	    BasicDBObject newDocument = new BasicDBObject();
		newDocument.putAll(dbObject);

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		collection.update(document, updateObj);
	    
	}
	
	public static Object getNextSequence(String name) throws Exception{
		
		String sequence_field = "seq";
		 
	    MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	    DB db = mongoClient.getDB("fitness_data");
	    DBCollection collection = db.getCollection("fitness_data");
	    
	    BasicDBObject document = new BasicDBObject();
	    document.put("_id", name);
	    
	    DBObject change = new BasicDBObject(sequence_field, 1);
	    DBObject update = new BasicDBObject("$inc", change);
	    
	    DBObject res = collection.findAndModify(document, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
	    System.out.println("Returning: " +res.get(sequence_field).toString());
	    return res.get(sequence_field).toString();
	}
}
