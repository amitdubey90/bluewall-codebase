package com.bluewall.feservices.serviceImpl;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.feservices.dao.RecommendationDao;
import com.bluewall.feservices.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    RecommendationDao dao;
    @Override
    public List<FoodInfo> getRecommendationsForUser(List<Integer> foodIdList, float calories, int count) {
        return dao.getRecommendationsForUser(foodIdList, calories, count);
    }
	@Override
	public List<Integer> getLatestPreferredFoodItem(int userId) {
		return dao.getLatestPreferredFoodItem(userId);
	}
}
