package com.bluewall.util.factory;

import com.bluewall.util.client.ClientInterface;
import com.bluewall.util.clientImpl.FitbitClient;
import com.bluewall.util.clientImpl.JawboneClient;
import com.bluewall.util.common.DeviceType;

public class DeviceClientFactory {

    /**
     * returns instance of a client based on the {@link DeviceType}
     *
     * @param deviceType
     * @return
     */
    public static ClientInterface getClientInstance(DeviceType deviceType) {
        ClientInterface instance = null;
        if (deviceType == DeviceType.FITBIT) {
            instance = new FitbitClient();
        } else if (deviceType == DeviceType.JAWBONE) {
            instance = new JawboneClient();
        }
        return instance;
    }
}
