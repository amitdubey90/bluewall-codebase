package com.bluewall.userDeviceDataMapper.queue;

import com.bluewall.userDeviceDataMapper.bean.ActivityLog;

public interface QueueManager<T> {

    /**
     *
     */
    void shutdown(boolean hard);

    /**
     *
     * @param request
     */
    void enqueueInbound(T request);

    /**
     *
     * @return
     */
    T dequeueInbound();

    /**
     *
     * @param request
     */
    void enqueueOutbound(ActivityLog request);

    /**
     *
     * @return
     */
    ActivityLog dequeueOutbound();

    /**
     *
     * @return
     */
    int getInboundSize();

    /**
     *
     * @return
     */
    int getOutboundSize();
}
