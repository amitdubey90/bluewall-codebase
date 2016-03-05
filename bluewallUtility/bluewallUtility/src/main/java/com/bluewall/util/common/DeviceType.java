package com.bluewall.util.common;

/**
 * Enum to represent all the supported devices types
 */
public enum DeviceType {
    FITBIT("fitbit", 10),
    JAWBONE("jawbone", 11);

    private String deviceName;
    private int deviceId;

    DeviceType(String deviceName, int deviceId) {
        this.deviceName = deviceName;
        this.deviceId = deviceId;
    }

    public String getName() {
        return this.deviceName;
    }

    public int getDeviceId() {
        return this.deviceId;
    }
}
