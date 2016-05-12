package com.bluewall.feservices.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.feservices.service.CalorieService;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@RestController
public class CalorieController {

	@Autowired
	CalorieService calorieService;

	/**
	 * Calorie Service to calculate total Calories Burnt and Consumed by the
	 * user in a day
	 */
	@RequestMapping(value = "/calorieDetails/{date}", method = RequestMethod.GET)
	public String getCalorieDetails(HttpSession session, @PathVariable("date") String date) {
		int userID = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userID = principal.getUserID();
			int sumCalorieBurnt = 0;
			int sumCalorieConsumed = 0;
			double netCalorie = 0;
			double percentCalorie = 0;
			double dailyCalories = 0;
			try {
				dailyCalories = calorieService.getTargetWeight(userID);
				sumCalorieBurnt = calorieService.getSumCaloriesBurnt(userID, date);
				SimpleDateFormat smpDate = new SimpleDateFormat("yyyy-MM-dd");
				if(date.equals(smpDate.format(new Date()))){
					sumCalorieBurnt += ((dailyCalories/24) * Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
				} else
				{
					sumCalorieBurnt += dailyCalories;
				}
				sumCalorieConsumed = calorieService.getSumCaloriesConsumed(userID, date);
				netCalorie = sumCalorieConsumed - sumCalorieBurnt;
				percentCalorie = (netCalorie / dailyCalories) * 100;
				
				if (percentCalorie > 100)
					percentCalorie = 100;
				if (percentCalorie < 0)
					percentCalorie = 0;
			} catch (Exception e) {
				log.error("Exception occured");
				e.printStackTrace();
			}
			return sumCalorieBurnt + "," + sumCalorieConsumed + "," + (int) netCalorie + "," + (int) percentCalorie + "," + dailyCalories;
		}
		return null;
	}
}