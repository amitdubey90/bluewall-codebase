package com.bluewall.userDeviceDataCollector.common;

public class Constants {

	public static final String FITBIT = "fitbit";
	public static final String JAWBONE = "jawbone";
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	public static final String DATABASE_NAME = "jdbc:mysql://user-db-instance.cqqcnirpnrkg.us-west-1.rds.amazonaws.com:3306/userDatabase";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "q1w2e3r4t5y6";

	public static final String FITBIT_REFRESH_TOKEN_URL = "https://api.fitbit.com/oauth2/token";
	public static final String FITBIT_APP_CLIENT_ID_CLIENT_SECRET = "227GC5:59316e79fe526a2549ca5faf33e9ac3f";
	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String BASIC = "Basic";
	public static final String UTF8 = "utf-8";
	public static final String GRANT_TYPE = "grant_type=refresh_token&refresh_token=";
	public static final String POST_METHOD = "POST";
	public static final String AUTHORIZATION = "Authorization";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String WWW_FORM_URL_ENCODED = "application/x-www-form-urlencoded";

	public static final String JAWBONE_REFRESH_TOKEN_URL = "https://jawbone.com/auth/oauth2/token";
	public static final String BEARER = "Bearer";
	public static final String JAWBONE_CLIENT_ID = "VQX6NU7FOAI";
	public static final String JAWBONE_CLIENT_SECRET = "8a2502a78670f6971045232de2e79c4c18c529cd";

	public static final String JAWBONE_ACTIVITY_API = "https://jawbone.com/nudge/api/v.1.1/users/@me/moves";
	public static final String GET_MEHTOD = "GET";
	public static final String APPLICATION_JSON = "application/json";
	public static final String FITBIT_ACTIVITY_API = "https://api.fitbitcom/1/user/-/activities/date/";

}
