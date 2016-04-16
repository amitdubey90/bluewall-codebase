package com.bluewall.feservices.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.service.ActivityService;
import com.bluewall.util.bean.UserActivityLog;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user/activity")
public class ActivityController {

	@Autowired
	ActivityService activityService;

	/*
	 * Activity service to fetch user's activity based on given userID
	 */

	@RequestMapping(value = "/activityLog", method = RequestMethod.GET)
	@ResponseBody
	public List<UserActivityLog> getActivityLog() {

		int userID=1;
		log.info("UserActivityLog service called");
		List<UserActivityLog> activityList = activityService.getUserActivityLogs(userID);
		log.info("User's activity logs fetched successfully");

		return activityList;
	}

	@RequestMapping(value = "/createActivity")
	@ResponseBody
	public void createActivity(@RequestBody UserActivityLog activity) {

		// fetch the user id from the session
		int userId = 1;
		activity.setLoggedFrom("Fitness Application");
		activity.setLogTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		activityService.createActivity(activity, userId);
	}

}
