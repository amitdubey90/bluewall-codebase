package jawbone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import mongodb.FitnessDataMongoDB;

public class Jawbone_apidata 
{
	public static void main(String[] args) throws Exception {
		Jawbone_apidata.getJawbonAPI();
	}
	
	public static String getJawbonAPI() throws Exception{
		String user_api = "https://jawbone.com/nudge/api/v.1.1/users/@me";
		StringBuffer sb = new StringBuffer();
		String userData = null;
		
		try {
			
			/* Configuration for Jawbone API call */
		    String encodedAuthorization = "DCEOB729f3iDVYqVCgoIAhfD77pd79dmFL5is7A-jise9Np2eJCyH88xFKXmlBhMxW38ahOAj648QJQG1FtnJVECdgRlo_GULMgGZS0EumxrKbZFiOmnmAPChBPDZ5JP";
			String auth = "Bearer "+ encodedAuthorization;
			
			URL url = new URL(user_api);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", auth);
			conn.setRequestProperty("Content-Type", "application/json");
			
			/* Reading Jawbone API Data */
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println("InputLine:" + inputLine);
				sb.append(inputLine);
			}
			in.close();
			
			/* Inserting Data Into Mongo */
			FitnessDataMongoDB fdm = new FitnessDataMongoDB();
			fdm.insert(sb.toString(), "Jawbone");
			
		}
		catch(UnsupportedEncodingException e){
			System.out.println("Error: "+e.getMessage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return userData;
		
	}
}
