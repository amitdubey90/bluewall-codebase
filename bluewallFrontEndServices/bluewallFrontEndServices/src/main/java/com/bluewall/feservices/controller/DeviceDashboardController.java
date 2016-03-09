package com.bluewall.feservices.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user/device")
public class DeviceDashboardController {

    @RequestMapping("/")
    public String getUserDeviceDashboard(HttpServletRequest request, HttpServletResponse response) {
        // TODO userid from session
        int userId = 1;

        boolean authorized = false;

        if (authorized)
            return "deviceDashboard";
        else
            return "addDevice";
    }
}
