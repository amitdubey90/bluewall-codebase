package com.bluewall.feservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.service.DeviceDataService;
import com.bluewall.util.bean.ActivityInfo;

@Controller
@RequestMapping("/user/activity")
public class UserActivityController {

	@Autowired
	DeviceDataService deviceDataService;
	
	@RequestMapping("/getRecentActivity")
	@ResponseBody
	public List<ActivityInfo> getUserActivityFeed() {
		
		// fetch the user id from the session
		String userId = "1";
		List<ActivityInfo> recentActivityList = deviceDataService.fetchUserActivityFeed(userId);
		
		 return recentActivityList;
	}
}
