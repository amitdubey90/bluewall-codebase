package com.bluewall.feservices.dao;

import com.bluewall.feservices.bean.FoodInfo;

import java.util.List;

public interface RecommendationDao {
    /**
     *
     * @param foodIdList
     * @param calories
     * @param count
     * @return
     */
    List<FoodInfo> getRecommendationsForUser(List<Integer> foodIdList, float calories, int count);

    List<Integer> getLatestPreferredFoodItem(int userId);
}
