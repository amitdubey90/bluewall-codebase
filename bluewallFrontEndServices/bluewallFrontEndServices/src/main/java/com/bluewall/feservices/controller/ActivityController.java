package com.bluewall.feservices.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.bean.UserPrincipal;
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
	public List<List<UserActivityLog>> getActivityLog(HttpSession session) {
		int userId = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userId = principal.getUserID();
			log.info("UserActivityLog service called");
			List<UserActivityLog> activityList = activityService.getUserActivityLogs(userId);
			log.info("User's activity logs fetched successfully");

			List<UserActivityLog> innerActivityList = new ArrayList<UserActivityLog>();
			List<List<UserActivityLog>> outerActivityList = new ArrayList<List<UserActivityLog>>();
			
			if(activityList.isEmpty()){
				innerActivityList = null;
				outerActivityList = null;
			}
			else{
				Date logDate = activityList.get(0).getActivityLogDate();
				String logTimeList = new SimpleDateFormat("yyyy-MM-dd").format(logDate);

				for (UserActivityLog activity : activityList) {
					String logTime = new SimpleDateFormat("yyyy-MM-dd").format(activity.getActivityLogDate());

					if (logTimeList.equalsIgnoreCase(logTime)) {
						innerActivityList.add(activity);
						logTimeList = logTime;
					} else {
						outerActivityList.add(innerActivityList);
						innerActivityList = new ArrayList<UserActivityLog>();
						innerActivityList.add(activity);
						logTimeList = logTime;
					}

				}
				outerActivityList.add(innerActivityList);
			}
			return outerActivityList;
		}
		// TODO: HANDLE ERROR HANDLING HERE
		return null;
	}

	@RequestMapping(value = "/recentActivityLog", method = RequestMethod.GET)
	@ResponseBody
	public List<UserActivityLog> getRecentActivityLog(HttpSession session) {
		int userId = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userId = principal.getUserID();
			List<UserActivityLog> activityList = activityService.getUserActivityLogs(userId);
			log.info("User's activity logs fetched successfully");
			return activityList;
		}
		return null;
	}

	@RequestMapping(value = "/createActivity")
	@ResponseBody
	public void createActivity(@RequestBody UserActivityLog activity, HttpSession session) {

		// fetch the user id from the session
		int userId = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userId = principal.getUserID();
			activity.setLoggedFrom("Fitness Application");
			activity.setLogTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			activityService.createActivity(activity, userId);
		}
	}
}
