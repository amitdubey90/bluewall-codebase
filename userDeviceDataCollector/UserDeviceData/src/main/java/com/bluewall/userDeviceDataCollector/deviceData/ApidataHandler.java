package com.bluewall.userDeviceDataCollector.deviceData;

import com.bluewall.userDeviceDataCollector.dao.FitnessData;
import com.bluewall.userDeviceDataCollector.dao.UserDetails;
import com.bluewall.userDeviceDataCollector.tokenHandler.TokenHandler;
import com.bluewall.util.bean.UserConnectedDevice;
import com.bluewall.util.client.ClientInterface;
import com.bluewall.util.common.DeviceType;
import com.bluewall.util.factory.ClientFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;

@Slf4j
public class ApidataHandler 
{
	public static void main(String[] args) throws Exception {
		ApidataHandler.getAPIData();
	}
		
	public static void getAPIData() throws Exception{
		
		try {
			UserDetails userDetails = new UserDetails();
			TokenHandler tokenHandler = new TokenHandler();
			ClientFactory instance = new ClientFactory();
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
					Device fitbit = instance.getClientInstance(DeviceType.FITBIT);
					userActivityInfoData =  fitbit.getUserActivityInfo("", "", accessToken);
					fdm.insertDeviceData(userActivityInfoData, userID, Constants.FITBIT);
				}	
				else{*/
				if(deviceID == 11){
					System.out.println("inserting jaawbone data");
					ClientInterface jawbone = instance.getClientInstance(DeviceType.JAWBONE);
					userActivityInfoData =  jawbone.getUserActivityInfo("1383289200", "1383289200", "DCEOB729f3iDVYqVCgoIAhfD77pd79dmFL5is7A-jise9Np2eJCyH2oQ71Ln3CCmxW38ahOAj648QJQG1FtnJVECdgRlo_GULMgGZS0EumxrKbZFiOmnmAPChBPDZ5JP");
					System.out.println("USer Data: " + userActivityInfoData);
					fdm.insertDeviceData(userActivityInfoData, userID, DeviceType.JAWBONE.getName());
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
