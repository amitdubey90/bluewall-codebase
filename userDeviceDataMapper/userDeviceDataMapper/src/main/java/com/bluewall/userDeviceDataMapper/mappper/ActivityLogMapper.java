package com.bluewall.userDeviceDataMapper.mappper;

import com.bluewall.userDeviceDataMapper.bean.ActivityLog;

public interface ActivityLogMapper<T> {

    ActivityLog map(T req);
}
