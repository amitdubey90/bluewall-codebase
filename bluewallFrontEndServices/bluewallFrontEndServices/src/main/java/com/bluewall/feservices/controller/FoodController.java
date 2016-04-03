package com.bluewall.feservices.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.feservices.bean.UserInfo;
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
	public void getUserFoodLog(@PathVariable("userID") int userID) {

		List<UserFood> userFoodLogList = new ArrayList<UserFood>();
		log.info("User food log service called");
		userFoodLogList = foodService.getUserFoodLog(userID);
		log.info("User food logs fetched successfully");

		for (UserFood foodLogIterator : userFoodLogList) {
			System.out.println("Food name: " + foodLogIterator.getName());
			System.out.println("Food type: " + foodLogIterator.getType());
			System.out.println("Food Calorie: " + foodLogIterator.getCalories());
			System.out.println("Food weight consumed: " + foodLogIterator.getWeightConsumed());
			System.out.println("Time when food is consumed: " + foodLogIterator.getFoodLogTime());
		}
	}

	/*
	 * create food logs for the user
	 */

	@RequestMapping(value = "/createFoodPlate", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void createFoodPlate(@RequestBody UserFood food) throws ParseException {

		// fetch user id from session
		int userID = 1;
		
		food.setLogTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		//TODO:check foodlog time date
		foodService.createFoodPlate(food, userID);
	}

	@RequestMapping(value = { "/getFoodItems" }, method = RequestMethod.GET)
	@ResponseBody
	public List<FoodInfo> getFoodItems(@RequestParam String foodName) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		UserInfo info = (UserInfo)auth.getPrincipal();
		List<FoodInfo> foodList = new ArrayList<FoodInfo>();

		foodList = foodService.getFoodInfo(foodName);
		return foodList;
	}

}
