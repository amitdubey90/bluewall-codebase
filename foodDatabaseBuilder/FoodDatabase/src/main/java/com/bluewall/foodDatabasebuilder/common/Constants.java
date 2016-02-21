package com.bluewall.foodDatabasebuilder.common;

public class Constants {

	public static final String DATABASE_NAME = "jdbc:mysql://user-db-instance.cqqcnirpnrkg.us-west-1.rds.amazonaws.com:3306/userDatabase";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "q1w2e3r4t5y6";
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	public static final String URL_PART_ONE = "http://api.nal.usda.gov/ndb/reports/?ndbno=";
	public static final String URL_PART_TWO = "&type=f&format=json&api_key=bdWrlWhOiBtimlxGsBWljMMsD0eZEaGszomrdqhI";
	public static final String FETCH_FOOD_ID_URL = "http://api.nal.usda.gov/ndb/list?format=json&lt=f&sort=id&api_key=bdWrlWhOiBtimlxGsBWljMMsD0eZEaGszomrdqhI&max=500&offset=";
	public static final String MONGO_DATABASE = "food-database";
	public static final String COLLECTION = "foodDetails";
	public static final String MONGO_HOST = "mongodb://bluewall-food-db:bluewall1234@ds011278.mongolab.com:11278/food-database";
	public static final String RETRY_PATH = "/Users/rainashastri/Desktop/RETRY.txt";
	public static final String NEW_LINE_SEPARATOR = "line.separator";
}
