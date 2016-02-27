package com.bluewall.feservices.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	private static final String SCOPE = "profile";
	private static final String REDIRECT_URI = "http://localhost:8080/callback";
	private static final String CLIENT_ID = "962513083976-1pv7uun6hj9ffemotrb363p1bgk8ihj4.apps.googleusercontent.com";
	private static final String APP_SECRET = "c_BV6eZgM-3XXB1kFosTevdW";
	private static final String DIALOG_OAUTH = "https://accounts.google.com/o/oauth2/auth";
	private static final String ACCESS_TOKEN = "https://accounts.google.com/o/oauth2/token";

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public void signin(HttpServletRequest request, HttpServletResponse response)
               throws Exception {
		try {
			//TODO: if already have a valid access token, no need to redirect, just login
			response.sendRedirect(DIALOG_OAUTH+"?client_id="+CLIENT_ID+
                              "&redirect_uri="+REDIRECT_URI+
                              "&scope="+SCOPE+
                              "&response_type=code");
			System.out.println("Exiting oauth");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/callback", params = "code", method = RequestMethod.GET)
	@ResponseBody
	public void accessCode(@RequestParam("code") String code,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
		try {
			response.setContentType("text/html");
			System.out.println("entering token");
			System.out.println("Code: " + code);
			String urlParameters = "code="
                    + code
                    + "&client_id=962513083976-1pv7uun6hj9ffemotrb363p1bgk8ihj4.apps.googleusercontent.com"
                    + "&client_secret=c_BV6eZgM-3XXB1kFosTevdW"
                    + "&redirect_uri=" +REDIRECT_URI
                    + "&grant_type=authorization_code";
			
			URL url = new URL(ACCESS_TOKEN);
			URLConnection urlConn = url.openConnection();
			urlConn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(
			urlConn.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			
			String line, outputString = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
            System.out.println(outputString);
            
            //get Access Token 
            JSONObject json = new JSONObject(outputString);
            String access_token = json.getString("access_token").toString();
            System.out.println(access_token);

          //get User Info 
            url = new URL(
                    "https://www.googleapis.com/oauth2/v1/userinfo?access_token="
                            + access_token);
            urlConn = url.openConnection();
            outputString = "";
            reader = new BufferedReader(new InputStreamReader(
                    urlConn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
            System.out.println(outputString);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
