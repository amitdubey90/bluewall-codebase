package com.bluewall.userDeviceDataMapper.util;

/**
 * Constant class
 */
public class Constants {

    // MySql Connection Settings
    public static final String MYSQL_RDS_URI = "jdbc:mysql://userdatabase.cjydixq0uvpi.us-west-1.rds.amazonaws.com/userDatabase";
    public static final String MYSQL_RDS_USER = "root";
    public static final String MYSQL_RDS_PASSWORD = "q1w2e3r4t5y6";
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    // Mongolab connection settings
    public static final String MONGO_LAB_URI = "mongodb://bluewallmongo:bluewall1234@ds061375.mongolab.com:61375/user-activity-raw";

    // Default values
    public static final String DEFAULT_ACTIVITY_NAME = "Device Summary";

    // Generic keys for mappers
    public static final String DEVICE_TYPE_KEY = "device_type";
    public static final String DEVICE_TYPE_FITBIT = "fitbit";
    public static final String DEVICE_TYPE_JAWBONE = "jawbone";

    // Fitbit api keys
    public static final String FITBIT_CALORIES_BURNT_KEY = "caloriesOut";
    public static final String FITBIT_USERID_KEY = "user_id";
    public static final double FITBIT_DEFAULT_DISTANCE = 0;
    public static final int FITBIT_LOGGED_FROM = 10;

    public static final String DOC_ID_KEY = "doc_id";
    public static final String ACTIVITY_LOG_DATE_KEY = "activity_log_date";
}
