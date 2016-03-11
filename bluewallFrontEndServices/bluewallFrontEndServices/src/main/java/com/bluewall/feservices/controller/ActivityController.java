package com.bluewall.feservices.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluewall.feservices.service.ActivityService;
import com.bluewall.util.bean.UserActivityLog;


@Slf4j
@RestController
public class ActivityController {
	
	@Autowired
	ActivityService activityService;
	
	/*
	 * Activity service to fetch user's activity based on given userID
	 */
	
	@RequestMapping(value = "/activityLog/{userID}", method = RequestMethod.GET)
	public void getActivityLog(@PathVariable("userID") int userID) {
		
		log.info("UserActivityLog service called");
		List<UserActivityLog> activityList = activityService.getUserActivityLogs(userID);
		log.info("User's activity logs fetched successfully");
		
		for (UserActivityLog iterateList : activityList){
			System.out.println("calories burnt "+iterateList.getCaloriesBurnt());
			System.out.println("type "+iterateList.getType());
			System.out.println("distance "+iterateList.getDistance());
			System.out.println("Name "+iterateList.getName());
			System.out.println("Start Time "+iterateList.getStartTime());
			System.out.println("Duration "+iterateList.getDuration());
		}
	}

}
