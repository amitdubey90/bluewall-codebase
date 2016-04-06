package com.bluewall.feservices.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluewall.feservices.service.CalorieService;

@Slf4j
@RestController
public class CalorieController {

	@Autowired
	CalorieService calorieService;

	/**
	 * Calorie Service to calculate total Calories Burnt and Consumed by the
	 * user in a day
	 */
	@RequestMapping(value = "/calorieDetails/{userID}", method = RequestMethod.GET)
	public String getCalorieDetails(@PathVariable("userID") int userID) {
		int sumCalorieBurnt = 0;
		int sumCalorieConsumed = 0;
		try {
			sumCalorieBurnt = calorieService.getSumCaloriesBurnt(userID);
			sumCalorieConsumed = calorieService.getSumCaloriesConsumed(userID);
		} catch (Exception e) {
			log.error("Exception occured");
		}
		return sumCalorieBurnt + "," + sumCalorieConsumed;
	}

}
