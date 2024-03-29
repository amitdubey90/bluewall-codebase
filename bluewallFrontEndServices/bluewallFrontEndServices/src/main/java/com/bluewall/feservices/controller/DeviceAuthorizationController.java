package com.bluewall.feservices.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.feservices.dao.UserDao;
import com.bluewall.feservices.service.DeviceAuthorizationSvcIfc;
import com.bluewall.feservices.util.Constants;
import com.bluewall.util.common.DeviceType;
import com.bluewall.util.factory.DeviceClientFactory;
import com.google.api.client.auth.oauth2.TokenResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/auth")
public class DeviceAuthorizationController {

    @Autowired
    private DeviceAuthorizationSvcIfc service;
    
    @Autowired
	UserDao userDao;

    @RequestMapping(value = "/initiateAuthorization/{deviceType}", method = RequestMethod.GET)
    public void startDeviceAuthorization(@PathVariable String deviceType, HttpServletResponse response,
                                         HttpSession session) {
        UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
        String userId = principal.getUserID() + "";
        String redirectUrl = "";
        try {

            if (DeviceType.FITBIT.getName().equals(deviceType)) {
                // TODO take user id from session
                redirectUrl = DeviceClientFactory.getClientInstance(DeviceType.FITBIT)
                        .getAuthorizationRequestUrl(userId, Constants.FITBIT_REDIRECT_URI);

            } else if (DeviceType.JAWBONE.getName().equals(deviceType)) {
                redirectUrl = DeviceClientFactory.getClientInstance(DeviceType.JAWBONE)
                        .getAuthorizationRequestUrl(userId, Constants.JAWBONE_REDIRECT_URI);

            }

            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/redirectFitbit")
    @ResponseBody
    public String handleAuthRedirectFitbit(@RequestParam("code") String code, HttpServletRequest request,HttpServletResponse response,HttpSession session) {
    	UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (principal != null) {
			session.setAttribute("userPrincipal", principal);
		}
        try {
            int userId = Integer.parseInt(request.getParameter("state"));
            log.debug("Received auth code from Fitbit for user {}", userId);

            log.debug("Attempting to fetch access token from Fitbit.");
            TokenResponse tokenResponse = DeviceClientFactory.getClientInstance(DeviceType.FITBIT)
                    .getAccessToken(code, Constants.FITBIT_REDIRECT_URI);
            log.info("Successfully retrieved access token for user {}", userId);
            
            if (!service.storeUserAccessCredentials(userId, DeviceType.FITBIT.getDeviceId(), tokenResponse)) {
            	response.sendRedirect("/#/user/deviceDashboard/"+principal);
            }
        } catch (NumberFormatException nfe) {
            log.error("Invalid user id in request : {}", request.getParameter("state"));
        } catch (IOException e) {
            log.error("Failed to retrieve access token from Fitbit: ", e);
        }
        try {
			response.sendRedirect("/#/user/deviceDashboard/"+principal);
		} catch (IOException e) {
			log.error("Failed to retrieve access token from Fitbit:", e);
		}
        return null;
    }

    @RequestMapping(value = "/redirectJawbone")
    @ResponseBody
    public String handleAuthRedirectJawbone(@RequestParam("code") String code, HttpServletRequest request,HttpSession session,HttpServletResponse response) {
        try {
            int userId = Integer.parseInt(request.getParameter("state"));
            log.debug("Received auth code from Jawbone for user {}", userId);

            log.debug("Attempting to fetch access token from Jawbone.");
            TokenResponse resp = DeviceClientFactory.getClientInstance(DeviceType.JAWBONE)
                    .getAccessToken(code, Constants.JAWBONE_REDIRECT_URI);
            log.info("Successfully retrieved access token for user {}", userId);

            if (service.storeUserAccessCredentials(userId, DeviceType.JAWBONE.getDeviceId(), resp)) {
                //return "Success";
            	UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
        		if (principal != null) {
        			session.setAttribute("userPrincipal", principal);
        		}
            	response.sendRedirect("/#/user/deviceDashboard/"+principal);
            }
        } catch (NumberFormatException nfe) {
            log.error("Invalid user id in request : {}", request.getParameter("state"));
        } catch (IOException e) {
            log.error("Failed to retrieve access token from Jawbone: ", e);
        }
        return null;
    }
}

