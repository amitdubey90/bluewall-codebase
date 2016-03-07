package com.bluewall.feservices.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bluewall.feservices.service.UserServices;
import com.bluewall.feservices.util.SQLQueries;
import com.bluewall.util.bean.UserProfile;

@RestController
public class Controllers {
		
    @RequestMapping("/hello")
    public String helloRestApi() {
        return "Hello world";
    }
   
   @Autowired
	 private UserServices userService;
	
	  @RequestMapping(value = "/register")
	  public void processRegistration(Map<String, Object> model) {
	        
		  	UserProfile userRegister = new UserProfile();    
	        //model.put("userRegister", userRegister);
	        
	        userRegister.setFirstName("TestDao");
	        userRegister.setLastName("testing");
	        userRegister.setEmailID("vrushTest@gmail.com");
	        userRegister.setContactNumber("7032971461");
	        userRegister.setActivityLevel("Level 3");
	        userRegister.setAge(23);
	        userRegister.setCurrentLocation("SF");
	        userRegister.setEndDate("05/06/2016");
	        userRegister.setStartDate("03/06/2016");
	        userRegister.setGender("M");
	        userRegister.setGoalType("Gain");
	        userRegister.setHeight(167);
	        userRegister.setPasswd("passwd");
	        userRegister.setTargetWeight(180);
	        userRegister.setWeight(150);
	        
	         
	       /* List<String> activityLevel = new ArrayList<>();
	        activityLevel.add("Level 1");
	        activityLevel.add("Level 2");
	        activityLevel.add("Level 3");
	        
	        List<String> goalType = new ArrayList<>();
	        goalType.add("Lose Weight");
	        goalType.add("Gain Weight");
	        goalType.add("Maintain Weight");
	        
	        model.put("goalType", goalType);*/
	        
	        userService.createUser(userRegister);
	         
	        //return "Registration";
	    }
   
  
    
    
}
