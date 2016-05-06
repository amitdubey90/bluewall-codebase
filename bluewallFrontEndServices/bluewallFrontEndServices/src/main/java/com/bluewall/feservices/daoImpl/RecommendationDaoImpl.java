package com.bluewall.feservices.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.feservices.dao.RecommendationDao;
import com.bluewall.feservices.util.Queries;

import lombok.extern.slf4j.Slf4j;
import scala.annotation.meta.setter;

@Slf4j
@Repository
public class RecommendationDaoImpl implements RecommendationDao {

	@Autowired
	DataSource dataSource;

	@Override
	public List<FoodInfo> getRecommendationsForUser(int foodId, float calories, int count) {
		List<FoodInfo> recommendations = new ArrayList<>();
		ResultSet rs = null;
		try (PreparedStatement pst = dataSource.getConnection().prepareStatement(Queries.GET_RECOMMENDATION_FOR_USER)) {
			int colId = 1;

			pst.setInt(colId++, foodId);
			pst.setDouble(colId++, calories);
			pst.setInt(colId++, count);

			rs = pst.executeQuery();
			FoodInfo fi = null;
			while (rs.next()) {
				fi = new FoodInfo();
				fi.setFoodName(rs.getString("name"));
				fi.setFoodId(rs.getInt("foodB"));
				fi.setFoodCalorie(rs.getDouble("foodBCalories"));
				recommendations.add(fi);
			}
			log.info("getRecommendationsForUser successful");
		} catch (SQLException e) {
			log.error("SqlException in getRecommendationsForUser {}", e);
		}
		finally{
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error("GET RECOMMENDATION: Result set object is not closed");
				}
			}
		}
		return recommendations;
	}

	@Override
	public int getLatestPreferredFoodItem(int userId) {
		int foodId = 0;
		ResultSet rs = null;
		try {
			PreparedStatement pst = dataSource.getConnection().prepareStatement(Queries.GET_MOST_PREFERRED_FOOD_ID);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			if (rs.next()) {
				foodId = rs.getInt("foodID");
			}
			log.debug("Successfully fetched most preferred foodId {}", foodId);

		} catch (SQLException e) {
			log.error("SqlException in getLatestPreferredFoodItem {}", e);
		}
		
		finally{
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error("GET LATEST PREF. FOOD: Result set object is not closed");
				}
			}
		}
		
		return foodId;
	}
}
