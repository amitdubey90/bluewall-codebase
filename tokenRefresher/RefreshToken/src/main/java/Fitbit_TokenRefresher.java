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
import java.util.Base64;
import org.json.JSONObject;

public class Fitbit_TokenRefresher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	
	public String getRefreshToken(){
		String refresh_URL = "https://api.fitbit.com/oauth2/token";
		String response;
		StringBuffer jsonResponse = new StringBuffer();
		String refreshToken = null;
		
		try {
			
			// Todo : refresh_token - pull from database.
			/* POST body parameters 
			 * grant_type
			 * refresh_token*/
			
		    String urlparams = "grant_type=refresh_token&refresh_token=d72b433738d143e7de5961950dcb6e7fea194b1787858812e40d42eb3672d599";
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
		        refreshToken = (String) obj.get("refresh_token");
		        System.out.println(refreshToken);
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
		}
		
		return refreshToken;
		
	}
}
