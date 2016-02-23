package com.bluewall.userDeviceDataMapper.mappper;

import com.bluewall.userDeviceDataMapper.bean.ActivityLog;

/**
 * Mapper interface to map any type to {@link ActivityLog}
 * @param <T>
 */
public interface ActivityLogMapper<T> {

    /**
     * Takes in any object and returns {@link ActivityLog} instance.
     * @param req
     * @return {@link ActivityLog} instance or null if fails to map
     */
    ActivityLog map(T req);
}
