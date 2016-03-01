package com.bluewall.userDeviceDataCollector.factory;

import com.bluewall.userDeviceDataCollector.client.ClientInterface;
import com.bluewall.userDeviceDataCollector.clientImpl.FitbitClient;
import com.bluewall.userDeviceDataCollector.clientImpl.JawboneClient;
import com.bluewall.userDeviceDataCollector.common.Constants;

/**
 *
 * 
 * @author rainashastri
 *
 */
public class ClientFactory {

	/**
	 * returns instance of a client based on the device name
	 * 
	 * @param deviceName
	 * @return
	 */
	public ClientInterface getClientInstance(String deviceName) {
		ClientInterface instance = null;
		if (deviceName.equalsIgnoreCase(Constants.FITBIT)) {
			instance = new FitbitClient();
		} else {
			instance = new JawboneClient();
		}
		return instance;
	}
}
