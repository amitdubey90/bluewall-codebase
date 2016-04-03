package com.bluewall.feservices.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.bean.UserInfo;
import com.bluewall.feservices.service.ActivityService;
import com.bluewall.util.bean.ActivityInfo;
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

	@RequestMapping(value = "/activityLog/{userID}", method = RequestMethod.GET)
	@ResponseBody
	public void getActivityLog(@PathVariable("userID") int userID) {

		log.info("UserActivityLog service called");
		List<UserActivityLog> activityList = activityService.getUserActivityLogs(userID);
		log.info("User's activity logs fetched successfully");

		for (UserActivityLog iterateList : activityList) {
			System.out.println("calories burnt: " + iterateList.getCaloriesBurnt());
			System.out.println("Activity type: " + iterateList.getType());
			System.out.println("distance: " + iterateList.getDistance());
			System.out.println("Name: " + iterateList.getName());
			System.out.println("Start Time: " + iterateList.getStartTime());
			System.out.println("MET: " + iterateList.getMET());
		}
	}

	@RequestMapping("/getRecentActivity")
	@ResponseBody
	
	public List<ActivityInfo> getUserActivityFeed() {

		// fetch the user id from the session
		int userId = 1;
		
		//System.out.println("******************"+info.getEmailID()+"******************");
		List<ActivityInfo> recentActivityList = activityService.fetchUserActivityFeedFromDevice(userId);

		return recentActivityList;
	}
	
	@RequestMapping(value = "/createActivity")
	@ResponseBody
	public void createActivity(Map <String, Object> model) {
		
		// fetch the user id from the session
		int userId = 1;
		
		java.util.Date date= new java.util.Date();
		
		UserActivityLog activityLog = new UserActivityLog();
		activityLog.setName("FirstActivity");
		activityLog.setType("firstType");
		activityLog.setDistance(66);
	//	activityLog.setDuration(4); send duration at back
		activityLog.setStartTime(new Timestamp(date.getTime()));
		activityLog.setCaloriesBurnt(2100);
		
		activityService.createActivity(activityLog, userId);
	}

}
