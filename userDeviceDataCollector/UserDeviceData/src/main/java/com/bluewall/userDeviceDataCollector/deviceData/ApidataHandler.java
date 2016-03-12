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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		Calendar calendar = Calendar.getInstance();
		
		List<UserConnectedDevice> userConnectedDeviceList = userDetails.getUserDetails();
		if(userConnectedDeviceList == null)
			log.info("No user id and device id found in database");
		else{
			log.debug("User ID and Device ID fetched of all users");
			for (UserConnectedDevice userConnectedDevice : userConnectedDeviceList) {
				int userID = userConnectedDevice.getUserID();
				int deviceID = userConnectedDevice.getDeviceID();
				String accessToken = null;
				try {
					accessToken = tokenHandler.getAccessToken(userID, deviceID);
					if(accessToken == null)
						log.info("Error fetching Access Token");
					else{
						log.info("Access Token Fetched from database");
						if(deviceID == 10){
							ClientInterface fitbit = DeviceClientFactory.getClientInstance(DeviceType.FITBIT);
							log.debug("Fitbit Instance created");
							
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							String currentDateTime = dateFormat.format(calendar.getTime());
							
							userActivityInfoData =  fitbit.getUserActivityInfo(currentDateTime, "", accessToken);
							if(userActivityInfoData == null)
								log.info("Error while fetching Fitbit data from User Activity Log API");
							else{
								log.info("Fitbit Data fetched from User Activity Log API");
								log.info("Inserting Fitbit Data in database");
								fdm.insertDeviceData(userActivityInfoData, userID, DeviceType.FITBIT.getName());
							}
						}	
						else{
							ClientInterface jawbone = DeviceClientFactory.getClientInstance(DeviceType.JAWBONE);
							log.debug("Jawbone Instance created");
							
							calendar.setTime(calendar.getTime());
							long currentmillis = calendar.getTimeInMillis();
							String curr_millis = String.valueOf(currentmillis);
							
							calendar.set(Calendar.HOUR_OF_DAY, 00);
							calendar.set(Calendar.MINUTE, 00);
							calendar.set(Calendar.SECOND, 00);
							calendar.set(Calendar.MILLISECOND, 00);
							
							long startmillis = calendar.getTimeInMillis();
							String start_millis = String.valueOf(startmillis);
							
							userActivityInfoData =  jawbone.getUserActivityInfo(start_millis, curr_millis, accessToken);
							if(userActivityInfoData == null)
								log.info("Error fetching Jawbone data from Moves API");
							else{
								log.info("Jawbone Data fetched from Moves API");
								log.info("Inserting Fitbit Data in database");
								fdm.insertDeviceData(userActivityInfoData, userID, DeviceType.JAWBONE.getName());
							}
						}
					}
				} catch (InstantiationException e) {
					log.error("Instantiation Exception Occured while establising SQL connection");
				} catch (IllegalAccessException e) {
					log.error("Illegal Access Exception Occured while establising SQL connection");
				} catch (ClassNotFoundException e) {
					log.error("Class Not Found Exception Occured while establising SQL connection");
				} catch (SQLException e) {
					log.error("SQL Exception Occured while establising connection");
				}
			}
		}
	}
}
