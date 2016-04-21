package com.bluewall.feservices.daoImpl;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.feservices.dao.RecommendationDao;
import com.bluewall.feservices.util.Queries;
import com.bluewall.feservices.util.SQLQueries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class RecommendationDaoImpl implements RecommendationDao {

    @Autowired
    DataSource dataSource;

    @Override
    public List<FoodInfo> getRecommendationsForUser(int foodId, float calories, int count) {
        List<FoodInfo> recommendations = new ArrayList<>();
        try (PreparedStatement pst = dataSource.getConnection().prepareStatement(Queries.GET_RECOMMENDATION_FOR_USER)) {
            int colId = 1;

            pst.setInt(colId++, foodId);
            pst.setDouble(colId++, calories);
            pst.setInt(colId++, count);

            ResultSet rs = pst.executeQuery();
            FoodInfo fi = null;
            while(rs.next()) {
                fi = new FoodInfo();
                fi.setFoodId(rs.getInt("foodB"));
                fi.setFoodCalorie(rs.getDouble("foodBCalories"));
                recommendations.add(fi);
            }
            log.info("getRecommendationsForUser successful");
        } catch (SQLException e) {
            log.error("SqlException in getRecommendationsForUser {}", e);
        }
        return recommendations;
    }
}
