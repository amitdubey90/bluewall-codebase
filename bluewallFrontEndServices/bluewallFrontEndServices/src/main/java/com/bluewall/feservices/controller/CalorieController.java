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
	@RequestMapping(value = "/calorieDetails/{userID}/{date}", method = RequestMethod.GET)
	public String getCalorieDetails(@PathVariable("userID") int userID, @PathVariable("date") String date) {
		System.out.println("Inside Calorie Controller");
		System.out.println("Date: " + date);
		int sumCalorieBurnt = 0;
		int sumCalorieConsumed = 0;
		/*int netCalorie = 0;
		int percentCalorie = 0;
		int targetWeight = 0;*/
		try {
			sumCalorieBurnt = calorieService.getSumCaloriesBurnt(userID, date);
			sumCalorieConsumed = calorieService.getSumCaloriesConsumed(userID, date);
			/*netCalorie = sumCalorieConsumed - sumCalorieBurnt;
			percentCalorie = (netCalorie / targetWeight) * 100; */
			System.out.println(sumCalorieBurnt);
			System.out.println(sumCalorieConsumed);
			/*System.out.println(netCalorie);
			System.out.println(percentCalorie);*/
		} catch (Exception e) {
			log.error("Exception occured");
			e.printStackTrace();
		}
		return sumCalorieBurnt + "," + sumCalorieConsumed /*+ "," + netCalorie + "," + percentCalorie*/;
	}

}
