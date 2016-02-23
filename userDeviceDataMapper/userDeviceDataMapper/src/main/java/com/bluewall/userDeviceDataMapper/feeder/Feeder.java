package com.bluewall.userDeviceDataMapper.feeder;

/**
 * Interface for implementing feeders for inbound mapper queues
 * @param <T>
 */
public interface Feeder<T> {
    /**
     * To enqueue a request on to inbound mapper
     * @param request
     */
    void enqueueToMapperQueue(T request);

    /**
     * Actually starts feeding records
     */
    void startFeeder();

    /**
     * Stop feeder and shutdown
     * @param hard - true for hard shutdown
     */
    void stopFeeder(boolean hard);
}
