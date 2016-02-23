package com.bluewall.userDeviceDataMapper.queue;

import com.bluewall.userDeviceDataMapper.bean.ActivityLog;

public interface QueueManager<T> {

    /**
     * Method to shutdown all queues and workers
     * @param hard - clears all queues if true
     */
    void shutdown(boolean hard);

    /**
     * Enqueues request to inbound mapper queue
     * @param request
     */
    void enqueueInbound(T request);

    /**
     * Takes and returns one element from inbound mapper queue
     * @return T from inbound mapper queue
     */
    T dequeueInbound();

    /**
     * Enqueues request to outbound queue
     * @param request
     */
    void enqueueOutbound(ActivityLog request);

    /**
     * Takes and returns one element from outbound queue
     * @return
     */
    ActivityLog dequeueOutbound();

    /**
     *
     * @return int - size of inbound mapper queue
     */
    int getInboundSize();

    /**
     *
     * @return int - size of outbound queue
     */
    int getOutboundSize();
}
