package com.bluewall.feservices.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.feservices.service.FoodService;
import com.bluewall.util.bean.UserFood;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller

@RequestMapping("/user/food")
public class FoodController {

	@Autowired
	FoodService foodService;

	/*
	 * getUserFoodLog service to return user's food logs
	 */

	@RequestMapping(value = "/foodLog", method = RequestMethod.GET)
	@ResponseBody
	public List<List<UserFood>> getUserFoodLog(HttpSession session) {
		int userId = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		
		if (null != principal) {
			userId = principal.getUserID();
			List<UserFood> userFoodLogList = new ArrayList<UserFood>();
			log.info("User food log service called");
			userFoodLogList = foodService.getUserFoodLog(userId);
			log.info("User food logs fetched successfully");
			
			List<UserFood> innerFoodList = new ArrayList<UserFood>();
			List<List<UserFood>> outerFoodList = new ArrayList<List<UserFood>>();
			
			if(userFoodLogList.isEmpty()){
				innerFoodList = null;
				outerFoodList = null;
			}
			else{
				Date foodLogDate = userFoodLogList.get(0).getFoodLogDate();
				String logTimeList = new SimpleDateFormat("yyyy-MM-dd").format(foodLogDate);
				
				for(UserFood userFoodLog : userFoodLogList){
					String logTime = new SimpleDateFormat("yyyy-MM-dd").format(userFoodLog.getFoodLogDate());
					
					if(logTimeList.equalsIgnoreCase(logTime)){
						innerFoodList.add(userFoodLog);
						logTimeList = logTime;
					}
					else{
						outerFoodList.add(innerFoodList);
						innerFoodList = new ArrayList<UserFood>();
						innerFoodList.add(userFoodLog);
						logTimeList = logTime;
					}
				}
				outerFoodList.add(innerFoodList);
			}
			return outerFoodList;
		}
		// TODO: HANDLE ERROR HANDLING HERE
		return null;
	}
		
	/*
	 * create food logs for the user
	 */

	@RequestMapping(value = "/createFoodPlate", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void createFoodPlate(@RequestBody UserFood food, HttpSession session) throws ParseException {

		int userID = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userID = principal.getUserID();
			food.setLogTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			// TODO:check foodlog time date
			foodService.createFoodPlate(food, userID);
		}
	}

	@RequestMapping(value = { "/getFoodItems" }, method = RequestMethod.GET)
	@ResponseBody
	public List<FoodInfo> getFoodItems(@RequestParam String foodName) {

		List<FoodInfo> foodList = new ArrayList<FoodInfo>();

		foodList = foodService.getFoodInfo(foodName);
		return foodList;
	}
}
