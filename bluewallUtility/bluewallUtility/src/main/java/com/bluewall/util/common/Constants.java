package com.bluewall.util.common;

public class Constants {

	public static final String REFRESH_TOKEN_KEY = "refresh_token";
	public static final String ACCESS_TOKEN_KEY = "access_token";

	// Fitbit
	public static final String FITBIT_BASE_URL = "https://api.fitbit.com/";
	public static final String FITBIT_OAUTH_CODE_URL = "https://www.fitbit.com/oauth2/authorize";
	public static final String FITBIT_OAUTH_TOKEN_URL = "https://api.fitbit.com/oauth2/token";

	public static final String FITBIT_REFRESH_TOKEN_PATH = "oauth2/token";
	public static final String FITBIT_ACTIVITY_API_PATH = "1/user/-/activities/date/";

	public static final String FITBIT_APP_ID = "227GV3";
	public static final String FITBIT_APP_CLIENT_SECRET = "15fb64d970f586701c20aee5fac1e58f";

	public static final String[] FITBIT_SCOPES = new String[]{"activity","nutrition", "heartrate",
			"location", "nutrition", "profile", "settings", "sleep", "social", "weight"};

	// Jawbone
	public static final String JAWBONE_BASE_URL = "https://jawbone.com/";
	public static final String JAWBONE_OAUTH_TOKEN_URL = "https://jawbone.com/auth/oauth2/token";
	public static final String JAWBONE_OAUTH_CODE_URL = "https://jawbone.com/auth/oauth2/auth";

	public static final String JAWBONE_REFRESH_TOKEN_PATH = "auth/oauth2/token";
	public static final String JAWBONE_ACTIVITY_PATH = "nudge/api/v.1.1/users/@me/moves";

	public static final String JAWBONE_APP_ID = "uSYRnLdNCDk";
	public static final String JAWBONE_APP_CLIENT_SECRET = "2af55b09c4cac8843fd53769c10a0ab5c2278cdd";

	public static final String[] JAWBONE_SCOPES = new String[]{"basic_read", "extended_read", "move_read", "meal_read"};
}
