package com.bluewall.userDeviceDataMapper.feeder;

public interface Feeder<T> {
    void enqueueToMapperQueue(T request);
    void startFeeder();
    void stopFeeder(boolean hard);
}
