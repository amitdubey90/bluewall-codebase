package com.Bluewall.PlatformIndependent;
/*
 * author: Vrushank Doshi
 * Date: 02/14/2016
 * Module: Get refresh token */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;

import org.json.JSONObject;

public class TokenRefresher {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub	
	}
	
	//Fetch refresh token from database.
	public static String getRefreshToken(Connection conn, String userID){
		Statement stmt = null;
		ResultSet rs = null;
		String refreshToken = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select refreshToken from UserConnectedDevice where userID = "+userID);;
			
			while (rs.next()){
				refreshToken = rs.getString("refreshToken");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			try 
			{ 
				if (rs != null) 
					rs.close(); 
			} 
			catch (SQLException e) 
			{ 
				e.printStackTrace(); 
			}
			try 
			{ 
				if (stmt != null) 
					stmt.close(); 
			} 
			catch (SQLException e) 
			{ 
				e.printStackTrace(); 
			}
			try 
			{ 
				if (conn != null) 
					conn.close(); 
			} 
			catch (SQLException e) 
			{ 
				e.printStackTrace(); 
			}
		}
		
		return refreshToken;
	}
	
	
	//Refresh access token and save it in database.
	public static String getRefreshedAccessToken(String userID){
		String refresh_URL = "https://api.fitbit.com/oauth2/token";
		String response;
		StringBuffer jsonResponse = new StringBuffer();
		String new_refreshToken = null,accessToken = null;
		Connection dbconn = DatabaseActivity.establishDBConnection("jdbc:mysql://user-db-instance.cqqcnirpnrkg.us-west-1.rds.amazonaws.com","userDatabase","root","q1w2e3r4t5y6");
	
		try {
			String old_refreshToken = getRefreshToken(dbconn, userID);
			String urlparams = "grant_type=refresh_token&refresh_token=" + old_refreshToken;
			
			String encodedAuthorization = Base64.getEncoder().encodeToString("229WBX:a5e08a68db05625a2578ce37f7639ad4".getBytes("utf-8"));
			String auth = "Basic "+ encodedAuthorization;
			
			URL url = new URL(refresh_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", auth);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoOutput(true);

			DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
			ds.writeBytes(urlparams);
			ds.flush();
			ds.close();
			
			int responseCode = conn.getResponseCode();
			
			if (responseCode != 200){
				throw new RuntimeException("Failed : HTTP error code : "
		                + conn.getResponseCode());			
			}
			else{
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        while ((response = br.readLine()) != null) {
		                jsonResponse.append(response);
		        }
		        br.close();
		        conn.disconnect();
		       
		        JSONObject obj = new JSONObject(jsonResponse.toString());
		        new_refreshToken = (String) obj.get("refresh_token");     
		        Statement stmt = dbconn.createStatement();
		        accessToken = (String) obj.get("access_token");
		        String updateTokens = "UPDATE UserConnectedDevice SET refreshToken = " + new_refreshToken +",accessToken = " + accessToken + " where userID = "+userID;
		        stmt.executeUpdate(updateTokens);
			}
		}
		catch(UnsupportedEncodingException e){
			System.out.println("Error: "+e.getMessage());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return accessToken;
	}
	
	
	// Module to check whether to refresh access token or not.
	@SuppressWarnings("deprecation")
	public static boolean checkTokenExpiry(Connection conn, int userID){
		ResultSet rs = null;
		Statement stmt = null;
		Timestamp creationTime = null;
	
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select creationTime from UserConnectedDevice where userID = "+userID);;
			
			while (rs.next()){
				creationTime = rs.getTimestamp("creationTime");
			}
				
			Date date= new Date();
	        long time = date.getTime(); 
	        Timestamp ts = new Timestamp(time);
	        
	        if (ts.getDate() == creationTime.getDate()){
	        	if ((ts.getHours() - creationTime.getHours()) > 1){
	        		return true;
	        	}
	        }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			try 
			{ 
				if (rs != null) 
					rs.close(); 
			} 
			catch (SQLException e) 
			{ 
				e.printStackTrace(); 
			}
			try 
			{ 
				if (stmt != null) 
					stmt.close(); 
			} 
			catch (SQLException e) 
			{ 
				e.printStackTrace(); 
			}
			try 
			{ 
				if (conn != null) 
					conn.close(); 
			} 
			catch (SQLException e) 
			{ 
				e.printStackTrace(); 
			}
		}
		return false;	
	}
}
