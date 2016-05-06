package com.bluewall.feservices.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.feservices.service.DeviceAuthorizationSvcIfc;
import com.bluewall.util.bean.UserConnectedDevice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user/device")
public class DeviceDashboardController {
	@Autowired
	private DeviceAuthorizationSvcIfc service;

	@RequestMapping("/")
	public String getUserDeviceDashboard(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		int userID = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userID = principal.getUserID();
			boolean authorized = false;
			if (authorized)
				return "deviceDashboard";
			else
				return "addDevice";
		}
		return null;
	}

	@RequestMapping(value="/checkIfUserHasDevice", method = RequestMethod.GET)
	@ResponseBody
	public UserConnectedDevice checkIfUserHasDevice(HttpSession session) {
		int userID = 0;
		UserConnectedDevice device = null;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userID = principal.getUserID();
			device = service.checkIfUserHasDevice(userID);

		}
		return device;
	}
}
