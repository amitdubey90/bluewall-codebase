package com.bluewall.feservices.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
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
	 * Calorie Service to calculate total Calories Burnt and Consumed by the user in a day
	 */
	@RequestMapping(value = "/calorieDetails", method = RequestMethod.GET)
	public void getCalorieDetails() {
		try {
			int sumCalorieBurnt = calorieService.getSumCaloriesBurnt(1);
			int sumCalorieConsumed = calorieService.getSumCaloriesConsumed(1);
			
			System.out.println(sumCalorieBurnt);
			System.out.println(sumCalorieConsumed);
		} catch (Exception e) {
			log.error("Exception occured");
		}
	}
	
}
