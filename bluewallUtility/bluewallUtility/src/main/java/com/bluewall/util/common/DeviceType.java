package com.bluewall.util.common;

public enum DeviceType {
    FITBIT("fitbit"),
    JAWBONE("jawbone");

    private String deviceName;

    DeviceType(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getName() {
        return this.deviceName;
    }
}
