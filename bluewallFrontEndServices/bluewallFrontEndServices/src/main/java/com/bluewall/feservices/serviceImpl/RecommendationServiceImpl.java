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
    public List<FoodInfo> getRecommendationsForUser(int foodId, float calories, int count) {
        return dao.getRecommendationsForUser(foodId, calories, count);
    }
}
