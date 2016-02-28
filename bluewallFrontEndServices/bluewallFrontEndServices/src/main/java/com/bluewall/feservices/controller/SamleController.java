package com.bluewall.feservices.controller;

import java.sql.Connection;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bluewall.feservices.dao.SqlDBConnections;
import com.bluewall.feservices.util.SQLQueries;

@RestController
public class SamleController {
	
	
    @RequestMapping("/hello")
    public String helloRestApi() {
        return "Hello world";
    }
    
   @RequestMapping("/login")
    public String login(){
	   System.out.println("Login class");
	   String username = "tewst@gmail.com";
	   String passwd = "testpassword";
	   
	   SQLQueries sqlQuery = new SQLQueries();
	   
	   if (sqlQuery.checkValidUser(username,passwd)){
		   return "Valid User";
	   }
	   
	   return "Invalid user";
    }
    
    
}
