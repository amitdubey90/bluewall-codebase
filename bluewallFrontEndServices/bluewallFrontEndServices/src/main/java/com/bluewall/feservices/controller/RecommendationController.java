package com.bluewall.feservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.feservices.service.RecommendationService;

@RestController
@RequestMapping("recommendation")
public class RecommendationController {

    @Autowired
	RecommendationService svc;

    @RequestMapping("get/{count}")
    public List<FoodInfo> getRecommendations(@PathVariable int count) {
        //TODO from session
        int userId = 70;
        //TODO calculate required calories
        
        int foodId = svc.getLatestPreferredFoodItem(userId);
        int requiredCalories = 500;
        return svc.getRecommendationsForUser(foodId, requiredCalories, count);
    }
}
