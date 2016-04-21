package com.bluewall.feservices.controller;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.feservices.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping("recommendation")
public class RecommendationController {

    @Autowired
    RecommendationService svc;

    @RequestMapping("get/{count}")
    public List<FoodInfo> getRecommendations(@PathVariable int count) {
        //TODO from session
        int userId = 70;
        //Todo get foodid from rating table
        //TODO calculate required calories
        int foodId = 1117;
        int requiredCalories = 600;
        return svc.getRecommendationsForUser(foodId, requiredCalories, count);
    }
}
