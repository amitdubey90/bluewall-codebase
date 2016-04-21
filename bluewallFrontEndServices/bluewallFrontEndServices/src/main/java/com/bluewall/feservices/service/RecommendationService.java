package com.bluewall.feservices.service;

import com.bluewall.feservices.bean.FoodInfo;

import java.util.List;

public interface RecommendationService {

    List<FoodInfo> getRecommendationsForUser(int foodId, float calories, int count);
}
