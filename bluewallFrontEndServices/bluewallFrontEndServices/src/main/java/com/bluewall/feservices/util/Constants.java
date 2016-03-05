package com.bluewall.feservices.util;

public class Constants {
    public static final String MYSQL_RDS_URI = "jdbc:mysql://user-db-instance.cqqcnirpnrkg.us-west-1.rds.amazonaws.com/userDatabase";
    public static final String MYSQL_RDS_USER = "root";
    public static final String MYSQL_RDS_PASSWORD = "q1w2e3r4t5y6";
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    
    /* Gmail SignIn API Details */
    public static final String SCOPE = "email profile";
    public static final String REDIRECT_URI = "http://localhost:8080/callback";
    public static final String CLIENT_ID = "604637540969-1qp88ujp4omksdomc0iit5ct3v4r4ecu.apps.googleusercontent.com";
    public static final String APP_SECRET = "Q8XEed4dP1FrhLxhzD-BP-8_";
    public static final String DIALOG_OAUTH = "https://accounts.google.com/o/oauth2/auth";
    public static final String ACCESS_TOKEN = "https://accounts.google.com/o/oauth2/token";

    public static final String FITBIT_REDIRECT_URI = "http://localhost:8080/auth/redirectFitbit";
    public static final String JAWBONE_REDIRECT_URI = "http://localhost:8080/auth/redirectJawbone";
	public static final String FACEBOOK_SCOPES = null;
}
