package com.bluewall.feservices.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bluewall.feservices.bean.UserPrincipal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user/device")
public class DeviceDashboardController {

	@RequestMapping("/")
    public String getUserDeviceDashboard(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
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
	
}
