package com.bluewall.userDeviceDataCollector.deviceData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bluewall.userDeviceDataCollector.bean.UserConnectedDevice;
import com.bluewall.userDeviceDataCollector.client.Device;
import com.bluewall.userDeviceDataCollector.common.Constants;
import com.bluewall.userDeviceDataCollector.dao.FitnessDataMongoDB;
import com.bluewall.userDeviceDataCollector.dao.UserDataMySql;
import com.bluewall.userDeviceDataCollector.factory.DeviceFactory;
import com.bluewall.userDeviceDataCollector.tokenHandler.TokenHandler;

public class FitbitApidata 
{
	public static void main(String[] args) throws Exception {
		FitbitApidata.getFitbitAPI();
	}
		
	public static String getFitbitAPI() throws Exception{
		
		String userData = null;
		
		try {
			UserDataMySql userDataMySql = new UserDataMySql();
			List<UserConnectedDevice> userConnectedDeviceList = userDataMySql.getUserID();
			TokenHandler tokenHandler = new TokenHandler();
			DeviceFactory fitbitInstance = new DeviceFactory();
			Device fitbit = fitbitInstance.getClientInstance(Constants.FITBIT);
			FitnessDataMongoDB fdm = new FitnessDataMongoDB();
			
			for (UserConnectedDevice userConnectedDevice : userConnectedDeviceList) {
				System.out.println("UserID: " + userConnectedDevice.getUserID());
				int userID = userConnectedDevice.getUserID();
				String accessToken = tokenHandler.getAccessToken(userID);
				String userActivityInfoData =  fitbit.getUserActivityInfo("", "", accessToken);
				fdm.insert(userActivityInfoData, Constants.FITBIT);
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
		
		return userData;
		
	}
}
