package com.bluewall.feservices.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.dao.ActivityDao;
import com.bluewall.feservices.service.ActivityService;
import com.bluewall.util.bean.UserActivityLog;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	ActivityDao activityDao;

	/**
	 * @param userID
	 * @return List of UserActivityLog objects
	 */

	@Override
	public List<UserActivityLog> getUserActivityLogs(int userID) {

		return activityDao.getUserActivityLogs(userID);

	}

	@Override
	public void createActivity(UserActivityLog userActivity, int userId) {

		activityDao.createActivity(userActivity, userId);

	}

}
