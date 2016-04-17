package com.bluewall.feservices.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	public List<UserFood> getUserFoodLog(HttpSession session) {
		int userID = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userID = principal.getUserID();
			List<UserFood> userFoodLogList = new ArrayList<UserFood>();
			log.info("User food log service called");
			userFoodLogList = foodService.getUserFoodLog(userID);
			log.info("User food logs fetched successfully");

			return userFoodLogList;
		}
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
