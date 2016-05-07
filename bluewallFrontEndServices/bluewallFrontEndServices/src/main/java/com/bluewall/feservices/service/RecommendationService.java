package com.bluewall.feservices.service;

import com.bluewall.feservices.bean.FoodInfo;

import java.util.List;

public interface RecommendationService {

    List<FoodInfo> getRecommendationsForUser(List<Integer> foodId, float calories, int count);

    List<Integer> getLatestPreferredFoodItem(int userId);
}
