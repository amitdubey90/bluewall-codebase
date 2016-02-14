package bluewall.foodDatabasebuilder.builder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bluewall.foodDatabasebuilder.common.Constants;
import bluewall.foodDatabasebuilder.common.Queries;



public class FoodDatabaseBuilder {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

		Connection conn = null;
		String str = null;

		/*
		 * A file to store list of failed food Ids
		 */
		File file = new File(Constants.RETRY_PATH);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWritter = new FileWriter(file, true);
		String newLine = System.getProperty(Constants.NEW_LINE_SEPARATOR);

		MongoClient mongo = new MongoClient(Constants.LOCALHOST, 27017);
		MongoDatabase db = mongo.getDatabase(Constants.MONGO_DATABASE);
		MongoCollection<Document> collection = db.getCollection(Constants.COLLECTION);

		Class.forName(Constants.DRIVER_CLASS);
		conn = DriverManager.getConnection(Constants.DATABASE_NAME, Constants.USERNAME, Constants.PASSWORD);
		String query = Queries.GET_FOOD_ID;
		PreparedStatement st = conn.prepareStatement(query);
		//st.setString(1, "false");
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			String foodId = rs.getString("foodId");
			try {
				// form endpoint url with food Id, to fetch food details
				str = Constants.URL_PART_ONE + foodId + Constants.URL_PART_TWO;
				URL url = new URL(str);
				Scanner scan = new Scanner(url.openStream());
				System.out.println("Id Invoked: " + foodId);
				StringBuffer strBuff = new StringBuffer();
				while (scan.hasNext())
					strBuff.append(scan.nextLine());
				scan.close();

				// Insert the fetched response in MongoDB
				JSONObject obj = new JSONObject(strBuff.toString());
				collection.insertOne(Document.parse(obj.toString()));
				System.out.println("Id Inserted: " + foodId);
				String updateQuery = Queries.UPDATE_FOOD_ID;
				PreparedStatement preparedStmt = conn.prepareStatement(updateQuery);
				preparedStmt.setString(1, "true");
				preparedStmt.setString(2, foodId);
				preparedStmt.executeUpdate();
			} catch (Exception e) {
				System.out.println("Id Failed: " + e.getMessage() + " " + foodId);
				System.out.println(e.getMessage());
				e.printStackTrace();
				fileWritter.write(foodId + newLine);
				fileWritter.flush();
				continue;
			}
		}
		fileWritter.close();
	}

}
