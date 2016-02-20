package fitbit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import mongodb.FitnessDataMongoDB;

public class Fitbit_apidata 
{
	public static void main(String[] args) throws Exception {
		Fitbit_apidata.getFitbitAPI();
	}
		
	public static String getFitbitAPI() throws Exception{
		String user_api = "https://api.fitbit.com/1/user/-/profile.json";
		StringBuffer sb = new StringBuffer();
		String userData = null;
		
		try {
			
			/* Configuration for Fitbit API call */
		    String encodedAuthorization = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NTU4NjA5MjQsInNjb3BlcyI6Indwcm8gd2xvYyB3bnV0IHdzZXQgd3NsZSB3d2VpIHdociB3YWN0IHdzb2MiLCJzdWIiOiIzTlg2N0MiLCJhdWQiOiIyMjlXQlgiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJpYXQiOjE0NTU4NTczMjR9.S9RH_-IBBk0-_wZykkFrsH1t5ApzSQEWT7eCOlbf1SI";
			String auth = "Bearer "+ encodedAuthorization;
			URL url = new URL(user_api);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", auth);
			
			/* Reading Fitbit API Data */
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			in.close();

			/* Inserting Data Into Mongo */
			FitnessDataMongoDB fdm = new FitnessDataMongoDB();
			fdm.insert(sb.toString(), "Fitbit");

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
		
		return userData;
		
	}
}
