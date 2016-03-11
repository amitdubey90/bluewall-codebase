package com.bluewall.feservices.service;

import java.util.List;

import com.bluewall.util.bean.ActivityInfo;

public interface DeviceDataService {

	List<ActivityInfo> fetchUserActivityFeed(String userId);

	

}
