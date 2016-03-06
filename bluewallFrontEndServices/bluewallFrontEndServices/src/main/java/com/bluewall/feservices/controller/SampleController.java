package com.bluewall.feservices.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bluewall.feservices.util.SQLQueries;

@RestController
public class SampleController {
		
    @RequestMapping("/hello")
    public String helloRestApi() {
        return "Hello world";
    }
   
    
   /*@RequestMapping(value = "/login")
    public ModelAndView login(@RequestParam("username") String userName,
    					@RequestParam("password") String passwd){
	   
	   ModelAndView model = new ModelAndView();
	   model.setViewName("login");
	   
	   userName = "tewst@gmail.com";
	   passwd = "testpassword";
	   
	   SQLQueries sqlQuery = new SQLQueries();
	   
	   if (sqlQuery.checkValidUser(userName,passwd)){
		   //return "Valid User";
	   }
	      //return "Invalid user";
	   return model;
    }*/
    
   
   @RequestMapping(value = "/signup", method = RequestMethod.POST)
   public void createUser(){
	   
   }
    
    
}
