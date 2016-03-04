package com.bluewall.userDeviceDataCollector.deviceData;

import com.bluewall.userDeviceDataCollector.dao.FitnessData;
import com.bluewall.userDeviceDataCollector.dao.UserDetails;
import com.bluewall.userDeviceDataCollector.tokenHandler.TokenHandler;
import com.bluewall.util.bean.UserConnectedDevice;
import com.bluewall.util.client.ClientInterface;
import com.bluewall.util.common.DeviceType;
import com.bluewall.util.factory.DeviceClientFactory;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Jenil
 *
 */

@Slf4j
public class ApidataHandler 
{
	public static void main(String[] args) throws Exception {
		ApidataHandler.getAPIData();
	}
		
	/**
	 * Fetching list of User ID and Device ID
	 * Calling Fitbit and Jawbone API based on access token
	 * Inserting API data into Mongo
	 */
	public static void getAPIData(){
		
		UserDetails userDetails = new UserDetails();
		TokenHandler tokenHandler = new TokenHandler();
		FitnessData fdm = new FitnessData();
		String userActivityInfoData;

		List<UserConnectedDevice> userConnectedDeviceList = userDetails.getUserDetails();
		log.debug("Fetched User ID and Device ID of all users");

		for (UserConnectedDevice userConnectedDevice : userConnectedDeviceList) {
			int userID = userConnectedDevice.getUserID();
			int deviceID = userConnectedDevice.getDeviceID();
			String accessToken = null;
			try {
				accessToken = tokenHandler.getAccessToken(userID, deviceID);
				log.info("Access Token Fetched from database");
			} catch (InstantiationException e) {
				log.error("Instantiation Exception Occured while establising SQL connection");
			} catch (IllegalAccessException e) {
				log.error("Illegal Access Exception Occured while establising SQL connection");
			} catch (ClassNotFoundException e) {
				log.error("Class Not Found Exception Occured while establising SQL connection");
			} catch (SQLException e) {
				log.error("SQL Exception Occured while establising connection");
			}
			
			if(deviceID == 10){
				ClientInterface fitbit = DeviceClientFactory.getClientInstance(DeviceType.FITBIT);
				log.debug("Fitbit Instance created");
				userActivityInfoData =  fitbit.getUserActivityInfo("", "", accessToken);
				log.info("Fitbit Data fetched from User Activity Log API");
				fdm.insertDeviceData(userActivityInfoData, userID, DeviceType.FITBIT.getName());
			}	
			else{
				ClientInterface jawbone = DeviceClientFactory.getClientInstance(DeviceType.JAWBONE);
				log.debug("Jawbone Instance created");
				userActivityInfoData =  jawbone.getUserActivityInfo("1383289200", "1383289200", accessToken);
				log.info("Jawbone Data fetched from User Activity Log API");
				fdm.insertDeviceData(userActivityInfoData, userID, DeviceType.JAWBONE.getName());
			}
		}
	}
}
