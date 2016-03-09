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

	public static final String[] FITBIT_SCOPES = new String[] { "activity", "nutrition", "heartrate", "location",
			"nutrition", "profile", "settings", "sleep", "social", "weight" };

	// Jawbone
	public static final String JAWBONE_BASE_URL = "https://jawbone.com/";
	public static final String JAWBONE_OAUTH_TOKEN_URL = "https://jawbone.com/auth/oauth2/token";
	public static final String JAWBONE_OAUTH_CODE_URL = "https://jawbone.com/auth/oauth2/auth";

	public static final String JAWBONE_REFRESH_TOKEN_PATH = "auth/oauth2/token";
	public static final String JAWBONE_ACTIVITY_PATH = "nudge/api/v.1.1/users/@me/moves";

	public static final String JAWBONE_APP_ID = "uSYRnLdNCDk";
	public static final String JAWBONE_APP_CLIENT_SECRET = "2af55b09c4cac8843fd53769c10a0ab5c2278cdd";

	public static final String[] JAWBONE_SCOPES = new String[] { "basic_read", "extended_read", "move_read",
			"meal_read" };

	/* Facebook SignIn Api Details */
	public static final String REDIRECT_URI = "http://localhost:8080/register/callback/facebook";
	public static final String FACEBOOK_SCOPE = "email,user_about_me";
	public static final String FACEBOOK_CLIENT_ID = "1690293301249102";
	public static final String FACEBOOK_APP_SECRET = "316eb6df004a0575ce9961815724ede9";
	public static final String FACEBOOK_DIALOG_OAUTH = "https://www.facebook.com/dialog/oauth";
	public static final String FACEBOOK_ACCESS_TOKEN = "https://graph.facebook.com/v2.3/oauth/access_token";
	public static final String FACEBOOK_USER_PROFILE = "https://graph.facebook.com/v2.5/me";
}
