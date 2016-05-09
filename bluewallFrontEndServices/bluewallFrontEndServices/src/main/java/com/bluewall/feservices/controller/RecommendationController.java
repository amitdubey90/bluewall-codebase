package com.bluewall.feservices.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.bluewall.feservices.service.CalorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.feservices.service.FoodService;
import com.bluewall.feservices.service.RecommendationService;

@RestController
@RequestMapping("recommendation")
public class RecommendationController {

	@Autowired
	RecommendationService svc;
	
	@Autowired
	FoodService foodService;
	@Autowired
	CalorieService calorieService;

	@RequestMapping("get/{count}")
	public List<FoodInfo> getRecommendations(@PathVariable int count, HttpSession session) {

		int userId = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userId = principal.getUserID();
			List<Integer> foodIdList = svc.getLatestPreferredFoodItem(userId);
			// TODO calculate required calories
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			String date = sdf.format(new Date());

			int sumCalorieBurnt = calorieService.getSumCaloriesBurnt(userId, date);
			int sumCalorieConsumed = calorieService.getSumCaloriesConsumed(userId, date);
			int dailyCalories = calorieService.getTargetWeight(userId);
			int netCalorie = sumCalorieConsumed - sumCalorieBurnt;
			netCalorie = netCalorie > 0 ? netCalorie : 0;

			int requiredCalories = (dailyCalories - netCalorie) / 3;

			return svc.getRecommendationsForUser(foodIdList, requiredCalories, count);
		}
		return null;
	}
	
	@RequestMapping(value = "/rateFood/{foodId}/{foodRating}", method = RequestMethod.POST)
	@ResponseBody
	public void rateFoodItems(@PathVariable int foodId, @PathVariable int foodRating, HttpSession session){
		int userID = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userID = principal.getUserID();
			foodService.rateFoodItems(foodId, foodRating, userID);
		}
	}
}
