package com.bluewall.foodDatabasebuilder.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.bluewall.foodDatabasebuilder.common.Constants;

/**
 * Inserting data in FoodInfo
 * 
 * @author rainashastri
 *
 */
public class FoodInfo {

	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(Constants.MONGO_HOST));
		MongoDatabase db = mongoClient.getDatabase(Constants.MONGO_DATABASE);
		MongoCollection<Document> food = db.getCollection(Constants.COLLECTION);

		BasicDBObject projectFileds = new BasicDBObject();
		projectFileds.put("report.food.ndbno", 1);
		projectFileds.put("report.food.name", 1);
		projectFileds.put("report.food.fg", 1);
		projectFileds.put("report.food.manu", 1);
		projectFileds.put("_id", 0);

		BasicDBObject obj = new BasicDBObject();

		FindIterable<Document> iterable = food.find(obj).projection(projectFileds);

		iterable.forEach(new Block<Document>() {

			public void apply(final Document document) {
				// System.out.println(document);
				Object obj = document.get("report");
				Object d = ((Document) obj).get("food");
				String ndbno = ((Document) d).get("ndbno").toString();
				int foodId = Integer.parseInt(ndbno);
				String name = ((Document) d).get("name").toString();
				String manufactuer = ((Document) d).getString("manu").toString();
				String category = ((Document) d).getString("fg").toString();
				insertIntoSql(foodId, name, category, manufactuer);

			}

			private void insertIntoSql(int foodId, String name, String category, String manufactuer) {

				Connection conn = null;
				PreparedStatement stmt = null;
				try {
					// STEP 2: Register JDBC driver
					Class.forName(Constants.DRIVER_CLASS);

					// STEP 3: Open a connection
					// System.out.println("Connecting to a selected
					// database...");
					conn = DriverManager.getConnection(Constants.DATABASE_NAME, Constants.USERNAME, Constants.PASSWORD);
					// System.out.println("Connected database successfully...");

					// STEP 4: Execute a query
					// System.out.println("Inserting records into the
					// table...");
					String sql = "Insert into FoodInfo (foodId,name,category,manufacturer) value(?,?,?,?)";
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, foodId);
					stmt.setString(2, name);
					stmt.setString(3, category);
					stmt.setString(4, manufactuer);
					stmt.executeUpdate();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// finally block used to close resources
					try {
						if (stmt != null)
							conn.close();
					} catch (SQLException se) {
						se.printStackTrace();
					} // do nothing
					try {
						if (conn != null)
							conn.close();
					} catch (SQLException se) {
						se.printStackTrace();
					}
				}
			}
		});

		// }

	}
}
