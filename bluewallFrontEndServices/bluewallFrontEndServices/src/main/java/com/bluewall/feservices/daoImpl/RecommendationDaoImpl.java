package com.bluewall.feservices.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import javax.sql.DataSource;

import com.bluewall.feservices.util.DatabaseResouceCloser;
import com.google.api.client.repackaged.com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.feservices.dao.RecommendationDao;
import com.bluewall.feservices.util.Queries;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class RecommendationDaoImpl implements RecommendationDao {

	@Autowired
	DataSource dataSource;

	@Override
	public List<FoodInfo> getRecommendationsForUser(List<Integer> foodIdList, float calories, int count) {
		Preconditions.checkNotNull(foodIdList);
		Preconditions.checkArgument(foodIdList.size() != 0, "Preferred food list is empty");

		//hack to remove repeated recommendation
		Set<Integer> set = new HashSet<>(foodIdList);
		foodIdList.clear();
		foodIdList.addAll(set);

		List<FoodInfo> recommendations = new ArrayList<>();
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement pst = null;
		try {
			// build the query
			StringBuffer query = null;
			for (int i : foodIdList) {
				query = new StringBuffer(Queries.GET_RECOMMENDATION_FOR_USER);
				query.append(Queries.GET_RECOMMENDATION_FOR_USER_CONDITION);
				connection = dataSource.getConnection();
				pst = connection.prepareStatement(query.toString());
				int colId = 1;
				pst.setInt(colId++, i);
				pst.setDouble(colId++, calories);
				pst.setInt(colId++, 2);
				rs = pst.executeQuery();
				FoodInfo fi = null;
				while (rs.next()) {
					fi = new FoodInfo();
					fi.setFoodName(rs.getString("name"));
					fi.setFoodCategory(rs.getString("category"));
					fi.setFoodId(rs.getInt("foodB"));
					fi.setFoodCalorie(rs.getDouble("foodBCalories"));
					fi.setImageUrl(rs.getString("imageUrl"));
					recommendations.add(fi);
				}
			}
			log.info("getRecommendationsForUser successful");
		} catch (SQLException e) {
			log.error("SqlException in getRecommendationsForUser {}", e);
		} finally {
			DatabaseResouceCloser.closeAllSilent(connection, pst, rs);
		}
		return recommendations;
	}

	@Override
	public List<Integer> getLatestPreferredFoodItem(int userId) {
		List<Integer> foodIdList = new ArrayList<>();
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement pst = null;

		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(Queries.GET_PREFERRED_FOOD_ID);
			int colIdx = 1;
			pst.setInt(colIdx++, userId);
			pst.setInt(colIdx++, userId);
			pst.setInt(colIdx++, userId);
			rs = pst.executeQuery();
			while (rs.next()) {
				foodIdList.add(rs.getInt("foodID"));
			}
			log.debug("Successfully fetched most preferred foodId {}", foodIdList);

		} catch (SQLException e) {
			log.error("SqlException in getLatestPreferredFoodItem {}", e);
		}

		finally {
			DatabaseResouceCloser.closeAllSilent(connection, pst, rs);
		}

		return foodIdList;
	}
}
