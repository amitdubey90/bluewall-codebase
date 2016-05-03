package com.bluewall.feservices.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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

	@RequestMapping("get/{count}")
	public List<FoodInfo> getRecommendations(@PathVariable int count, HttpSession session) {

		int userId = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userId = principal.getUserID();
			int foodId = svc.getLatestPreferredFoodItem(userId);
			System.out.println("Preferred Food Id: " + foodId);
			// TODO calculate required calories
			int requiredCalories = 500;
			return svc.getRecommendationsForUser(foodId, requiredCalories, count);
		}
		return null;
	}
	
	@RequestMapping(value = "/rateFood/{foodId}/{foodRating}", method = RequestMethod.POST)
	@ResponseBody
	public void rateFoodItems(@PathVariable int foodId, @PathVariable int foodRating, HttpSession session){
		System.out.println(foodId);
		System.out.println(foodRating);
		int userID = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userID = principal.getUserID();
			foodService.rateFoodItems(foodId, foodRating, userID);
		}
	}
}
