package bluewall.foodDatabasebuilder.builder;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import bluewall.foodDatabasebuilder.common.Constants;
import bluewall.foodDatabasebuilder.common.Queries;

/*
 * This class fetches list of food Ids from usda food api and stores it under the mysql db
 */
class FoodIdCollector {

	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement stmt = null;

		int count = 0;

		try {
			while (count <= 9000) {

				String url = Constants.FETCH_FOOD_ID_URL + count;
				URL apiString = new URL(url.toString());
				Scanner scan = new Scanner(apiString.openStream());
				StringBuffer strBuff = new StringBuffer();
				while (scan.hasNext())
					strBuff.append(scan.nextLine());
				scan.close();

				JSONObject obj = new JSONObject(strBuff.toString());

				JSONObject reqlist = obj.getJSONObject("list");
				JSONArray items = reqlist.getJSONArray("item");

				for (int i = 0; i < items.length(); i++) {
					JSONObject json = items.getJSONObject(i);
					String foodId = json.getString("id");
					try {

						Class.forName(Constants.DRIVER_CLASS);
						conn = DriverManager.getConnection(Constants.DATABASE_NAME, Constants.USERNAME,
								Constants.PASSWORD);
						String sql = Queries.INSERT_FOOD_ID;
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, foodId);
						stmt.setString(2, "false");
						stmt.executeUpdate();
					} catch (Exception e) {
						System.out.println("An unknown exception has occured while executing SQL Query");
					} finally {
						try {
							if (stmt != null)
								conn.close();
						} catch (SQLException se) {
							System.out.println("SQL Exception has occured");
						}

					}
				}
				count += 500;

			}
		} catch (Exception e) {
			System.out.println("An unknow exception has occured");
		}

	}

}
