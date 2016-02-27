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
    	
    	return "Provide your login details";
    }
    
    
}
