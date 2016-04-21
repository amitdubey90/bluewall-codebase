package com.bluewall.feservices.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.feservices.service.RecommendationService;

@RestController
@RequestMapping("recommendation")
public class RecommendationController {

	@Autowired
	RecommendationService svc;

	@RequestMapping("get/{count}")
	public List<FoodInfo> getRecommendations(@PathVariable int count, HttpSession session) {

		int userId = 0;
		UserPrincipal principal = (UserPrincipal) session.getAttribute("userPrincipal");
		if (null != principal) {
			userId = principal.getUserID();
			int foodId = svc.getLatestPreferredFoodItem(userId);
			// TODO calculate required calories
			int requiredCalories = 500;
			return svc.getRecommendationsForUser(foodId, requiredCalories, count);
		}
		return null;
	}
}
