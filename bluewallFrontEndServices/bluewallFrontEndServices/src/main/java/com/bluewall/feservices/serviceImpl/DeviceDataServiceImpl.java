package com.bluewall.feservices.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.dao.UserActivityDao;
import com.bluewall.feservices.service.DeviceDataService;
import com.bluewall.util.bean.ActivityInfo;
import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.client.ClientInterface;
import com.bluewall.util.common.DeviceType;
import com.bluewall.util.factory.DeviceClientFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ComponentScan({ "com.bluewall.feservices.daoImpl" })
@Service

public class DeviceDataServiceImpl implements DeviceDataService {

	@Autowired
	UserActivityDao activityDao;

	@Override
	public List<ActivityInfo> fetchUserActivityFeed(String userId) {
		List<ActivityInfo> activityInfoList = new ArrayList<ActivityInfo>();
		try {
			ClientInterface deviceClient = null;
			ActivityInfo info = null;
			ObjectMapper mapper = null;

			UserCredential credentials = activityDao.getUserDeviceInfo(userId);
			if (null != credentials) {
				if (credentials.getDeviceID() == 10) {
					deviceClient = DeviceClientFactory.getClientInstance(DeviceType.FITBIT);
				} else if (credentials.getDeviceID() == 11) {
					deviceClient = DeviceClientFactory.getClientInstance(DeviceType.JAWBONE);
				}
				String recentActivity = "[ { \"activityId\":1030, \"calories\":1721, \"description\":\"Moderate - 12 to 13.9mph\", \"distance\":1, \"duration\":3723000, \"name\":\"Bicycling\" }, { \"activityId\":12030, \"calories\":1124, \"description\":\"Running - 5 mph (12 min/mile)\", \"distance\":2, \"duration\":7322000, \"name\":\"Running\" } ]";
				// deviceClient.getRecentUserActivity(credentials.getAccessToken());
				if (null != recentActivity) {
					JSONArray activityArrObj = new JSONArray(recentActivity);
					for (Object jsonObj : activityArrObj) {
						info = new ActivityInfo();
						mapper = new ObjectMapper();
						try {
							info = mapper.readValue(jsonObj.toString(), ActivityInfo.class);
							activityInfoList.add(info);
						} catch (IOException e) {
							log.error("An IO Exception occured while parsing JSON array");
						}
					}
				}
			}
		} catch (Exception exp) {
			log.error("An Unknown Exception occured");
		}

		return activityInfoList;
	}

}