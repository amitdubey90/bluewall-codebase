package com.bluewall.foodDatabasebuilder.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.bluewall.foodDatabasebuilder.common.Constants;
import com.bluewall.foodDatabasebuilder.common.Queries;

/**
 * This class fetches food nutrient info from mongoDB and maps it under the
 * columns in FoodNutrientPer100Gram table.
 * 
 * @author rainashastri
 *
 */
@SuppressWarnings("unused")
public class FoodNutrientInfoXgms {

	public static void main(String[] args) {

		MongoClient mongoClient = new MongoClient(new MongoClientURI(Constants.MONGO_HOST));
		MongoDatabase db = mongoClient.getDatabase(Constants.MONGO_DATABASE);

		MongoCollection<Document> food = db.getCollection(Constants.COLLECTION);

		BasicDBObject projectFileds = new BasicDBObject();

		projectFileds.put("_id", 0);

		BasicDBObject obj = new BasicDBObject();

		FindIterable<Document> iterable = food.find(obj).projection(projectFileds);

		iterable.forEach(new Block<Document>() {

		
			@SuppressWarnings("rawtypes")
			public void apply(final Document document) {

				double water = 0.0, kcal = 0.0, sugar = 0.0, fat = 0.0, carbs = 0.0, protein = 0.0,equivalentGrams=0.0;
				Object obj = document.get("report");
				Object d = ((Document) obj).get("food");
				String ndbno = ((Document) d).get("ndbno").toString();
				System.out.println("FOOD ID: "+ndbno);
				int foodId = Integer.parseInt(ndbno);
				
				String name = ((Document) d).get("name").toString();
				List nutrientsArr = (ArrayList) ((Document) d).get("nutrients");
				for (int i = 0; i < nutrientsArr.size(); i++) {
					Document doc = (Document) nutrientsArr.get(i);
					int nutrientId = (Integer) doc.get("nutrient_id");
					List measuresArr = (ArrayList) doc.get("measures");
					if((null!=measuresArr)&&(!measuresArr.isEmpty())&&(null!=(Document) measuresArr.get(0))){
						Document measureDoc = (Document) measuresArr.get(0);
						equivalentGrams = Double.parseDouble(measureDoc.get("eqv").toString());
						if (nutrientId == 255) {
							water = Double.parseDouble(measureDoc.get("value").toString());
						}
						if (nutrientId == 208) {
							kcal = Double.parseDouble(measureDoc.get("value").toString());
						}
						if (nutrientId == 203) {
							protein = Double.parseDouble(measureDoc.get("value").toString());
						}
						if (nutrientId == 204) {
							fat = Double.parseDouble(measureDoc.get("value").toString());
						}
						if (nutrientId == 205) {
							carbs = Double.parseDouble(measureDoc.get("value").toString());
						}
						if (nutrientId == 269) {
							sugar = Double.parseDouble(measureDoc.get("value").toString());
						}
					}
					
				}

				insertIntoSql(foodId,equivalentGrams, water, kcal, protein, fat, carbs, sugar);

			}

			private void insertIntoSql(int foodId, double equivalentGrams,double water, double kcal, double protein, double fat, double carbs,
					double sugar) {

				Connection conn = null;
				PreparedStatement stmt = null;
				try {
					Class.forName(Constants.DRIVER_CLASS);

					System.out.println("Connecting to a selected database...");
					conn = DriverManager.getConnection(Constants.DATABASE_NAME, Constants.USERNAME, Constants.PASSWORD);
					System.out.println("Connected database successfully...");

					System.out.println("Inserting records into the table...");
					String sql = Queries.INSERT_FOOD_NUTRIENT_INFO;
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, foodId);
					stmt.setDouble(2, equivalentGrams);
					stmt.setDouble(3, water);
					stmt.setDouble(4, kcal);
					stmt.setDouble(5, protein);
					stmt.setDouble(6, fat);
					stmt.setDouble(7, carbs);
					stmt.setDouble(8, sugar);
					stmt.executeUpdate();

				} catch (Exception e) {
					System.out.println("An unknown exception has occured");
				} finally {

					try {
						if (stmt != null)
							conn.close();
					} catch (SQLException se) {
						System.out.println("SQLException has occured");
					}

				}
			}
		});

	}

}
