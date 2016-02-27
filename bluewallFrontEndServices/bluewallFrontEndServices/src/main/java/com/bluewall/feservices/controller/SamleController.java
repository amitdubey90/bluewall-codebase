package com.bluewall.feservices.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SamleController {

    @RequestMapping("/hello")
    public String helloRestApi() {
        return "Hello world";
    }
    
   @RequestMapping("/login")
    public String login(){
    	
	   String username = "vrushank.doshi90@gmail.com";
	   String passwd = "testpassword";
	   
	   
	   return "Provide your login details";
    }
    
    
}
