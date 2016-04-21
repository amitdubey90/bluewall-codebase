package com.bluewall.feservices.dao;

import com.bluewall.feservices.bean.FoodInfo;

import java.util.List;

public interface RecommendationDao {
    /**
     *
     * @param foodId
     * @param calories
     * @param count
     * @return
     */
    List<FoodInfo> getRecommendationsForUser(int foodId, float calories, int count);
}
