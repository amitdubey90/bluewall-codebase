package com.bluewall.userDeviceDataCollector.deviceData;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;
import com.bluewall.userDeviceDataCollector.bean.UserConnectedDevice;
import com.bluewall.userDeviceDataCollector.client.Device;
import com.bluewall.userDeviceDataCollector.common.Constants;
import com.bluewall.userDeviceDataCollector.dao.FitnessData;
import com.bluewall.userDeviceDataCollector.dao.UserDetails;
import com.bluewall.userDeviceDataCollector.factory.DeviceFactory;
import com.bluewall.userDeviceDataCollector.tokenHandler.TokenHandler;

public class ApidataHandler 
{
	public static void main(String[] args) throws Exception {
		ApidataHandler.getAPIData();
	}
		
	public static void getAPIData() throws Exception{
		
		try {
			UserDetails userDetails = new UserDetails();
			TokenHandler tokenHandler = new TokenHandler();
			DeviceFactory instance = new DeviceFactory();
			FitnessData fdm = new FitnessData();
			String userActivityInfoData;
			
			List<UserConnectedDevice> userConnectedDeviceList = userDetails.getUserDetails();
			
			for (UserConnectedDevice userConnectedDevice : userConnectedDeviceList) {
				System.out.println("UserID: " + userConnectedDevice.getUserID() + " " 
						+ "DeviceID: " + userConnectedDevice.getDeviceID());
				int userID = userConnectedDevice.getUserID();
				int deviceID = userConnectedDevice.getDeviceID();
				//String accessToken = tokenHandler.getAccessToken(userID, deviceID);
				/*if(deviceID == 10){
					System.out.println("inserting fitbit data");
					Device fitbit = instance.getClientInstance(Constants.FITBIT);
					userActivityInfoData =  fitbit.getUserActivityInfo("", "", accessToken);
					fdm.insertDeviceData(userActivityInfoData, userID, Constants.FITBIT);
				}	
				else{*/
				if(deviceID == 11){
					System.out.println("inserting jaawbone data");
					Device jawbone = instance.getClientInstance(Constants.JAWBONE);
					userActivityInfoData =  jawbone.getUserActivityInfo("1383289200", "1383289200", "DCEOB729f3iDVYqVCgoIAhfD77pd79dmFL5is7A-jise9Np2eJCyH2oQ71Ln3CCmxW38ahOAj648QJQG1FtnJVECdgRlo_GULMgGZS0EumxrKbZFiOmnmAPChBPDZ5JP");
					System.out.println("USer Data: " + userActivityInfoData);
					fdm.insertDeviceData(userActivityInfoData, userID, Constants.JAWBONE);
				}
			}
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
	}
}
