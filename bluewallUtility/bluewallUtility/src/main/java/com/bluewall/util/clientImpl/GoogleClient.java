package com.bluewall.util.clientImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;

import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.bean.UserProfile;
import com.bluewall.util.client.SocialConnectionProvidersInterface;
import com.bluewall.util.common.Constants;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;

@Slf4j
public class GoogleClient implements SocialConnectionProvidersInterface {

	@Override
	public UserCredential getNewAccessToken(String code) {
		
		String urlParameters = "code="
                + code
                + "&client_id="+Constants.GMAIL_CLIENT_ID
                + "&client_secret="+Constants.GMAIL_APP_SECRET
                + "&redirect_uri=" +Constants.GMAIL_REDIRECT_URI
                + "&grant_type=authorization_code";
		
		UserCredential creds = new UserCredential();
		URL url;
		try {
			url = new URL(Constants.GMAIL_ACCESS_TOKEN);
			URLConnection urlConn = url.openConnection();
			urlConn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(urlConn.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			
			String line, outputString = "";
	        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
	        while ((line = reader.readLine()) != null) {
	            outputString += line;
	        }
	        JSONObject json = new JSONObject(outputString);
	        creds.setAccessToken(json.getString("access_token").toString());
	        creds.setExpirationTime(json.getLong("expires_in"));
	        creds.setRefreshToken(json.getString("id_token").toString());
		} catch (MalformedURLException e) {
			log.error("Malformed URL Exception Occured in Google Client");
		} catch (IOException e) {
			log.error("IO Exception Occured in Google Client");
		}
        return creds;
	}

	@Override
	public UserProfile fetchUserProfile(UserCredential newUserCreds) {
		URL url;
		URLConnection urlConn;
		String line, outputString = "";
		UserProfile user = new UserProfile();
		try {
			url = new URL(Constants.GMAIL_USER_INFO + "?access_token="+ newUserCreds.getAccessToken());
			urlConn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
	        while ((line = reader.readLine()) != null) {
	            outputString += line;
	        }
	        JSONObject json = new JSONObject(outputString);
	        user.setEmailID(json.getString("email").toString());
	        user.setFirstName(json.getString("given_name").toString());
	        user.setLastName(json.getString("family_name").toString());
	        user.setGender(json.getString("gender").toString());
	        System.out.println(outputString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public UserCredential getAccessAndRefreshToken(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String AuthorizationRequestUrl() {
		return new AuthorizationRequestUrl(Constants.GMAIL_DIALOG_OAUTH, Constants.GMAIL_CLIENT_ID,
				Arrays.asList("code")).set("scope", Constants.GMAIL_SCOPE).setRedirectUri(Constants.GMAIL_REDIRECT_URI)
						.build();
	}

}
