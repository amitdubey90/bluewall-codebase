package com.bluewall.feservices.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.service.FoodService;
import com.bluewall.util.bean.UserFood;

@Slf4j
@Controller
@RequestMapping("/user/food")
public class FoodController {
	
	@Autowired
	FoodService foodService;
	
	/*
	 * getUserFoodLog service to return user's food logs
	 */
	
	@RequestMapping(value = "/foodLog/{userID}", method = RequestMethod.GET)
	@ResponseBody
	public void getUserFoodLog(@PathVariable("userID") int userID){
		
			List<UserFood> userFoodLogList = new ArrayList<UserFood>();
			
			log.info("User food log service called");
			userFoodLogList = foodService.getUserFoodLog(userID);
			log.info("User food logs fetched successfully");
			
			for (UserFood foodLogIterator : userFoodLogList){
				System.out.println("Food name: "+ foodLogIterator.getName());
				System.out.println("Food type: " + foodLogIterator.getType());
				System.out.println("Food Calorie: " + foodLogIterator.getCalories());
				System.out.println("Food weight consumed: " + foodLogIterator.getWeightConsumed());
				System.out.println("Time when food is consumed: " + foodLogIterator.getTimeConsumed());
			}
	}
	
	/*
	 * create food logs for the user
	 */
	
	@RequestMapping(value = "/createFoodPlate")
	@ResponseBody
	public void createFoodPlate(){
		
		//fetch user id from session
		int userID = 1;
		
		java.util.Date date= new java.util.Date();
		UserFood createFood = new UserFood();
		createFood.setName("Subway");
		createFood.setType("Sandwich");
		createFood.setManufacturer("Subway");
		createFood.setCategory("Subway");
		createFood.setCalories(200);
		createFood.setTimeConsumed(new Timestamp(date.getTime()));
		createFood.setWeightConsumed(2);
		createFood.setFoodLogTime(new Timestamp(date.getTime()));
		foodService.createFoodPlate(createFood, userID);
	}
	
}
