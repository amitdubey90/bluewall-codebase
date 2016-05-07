package com.bluewall.feservices.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

import com.bluewall.feservices.dao.CalorieDao;
import com.bluewall.feservices.util.Queries;

/**
 * @author Jenil
 *
 */
@Slf4j
@Repository
public class CalorieDaoImpl implements CalorieDao {

	@Autowired
	DataSource dataSource;

	@Override
	public int getSumCaloriesBurnt(int userID, String date) {
		
		ResultSet resultSet = null;
		int totalCaloriesBurnt = 0;
		log.info("Fetching Sum of Caloreies Burnt for a day");
		try 
		{
			String sql = "select sum(sum) as totalCaloriesBurnt from (select round(sum(caloriesBurnt)) as sum from ActivityLog where loggedFrom=14 and userID="
					+ userID
					+ " and activityLogDate = '"
					+ date
					+ "' union all select max(caloriesBurnt) as sum from ActivityLog where (loggedFrom=10 or loggedFrom=11) and userID="
					+ userID
					+ " and activityLogDate = '"
					+ date
					+ "') as totalCaloriesBurnt";
			
			resultSet = dataSource.getConnection().prepareStatement(sql).executeQuery();
			log.info("GET SUM CALORIES BURNT: Data fetched from database successfully");
			if (resultSet.next())
				totalCaloriesBurnt = resultSet.getInt("totalCaloriesBurnt");
		} catch (SQLException e) {
			log.error("GET SUM CALORIES BURNT: SQL Exception occured while fetching user details");
		}
		finally{
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					log.error("GET SUM CALORIES BURNT: Result set object is not closed");
				}
			}

		}
		return totalCaloriesBurnt;
	}

	@Override
	public int getSumCaloriesConsumed(int userID, String date) {

		int totalCaloriesConsumed = 0;
		ResultSet resultSet = null;
		log.info("Fetching Sum of Caloreies Consumed for a day");	
		
		try {
			resultSet = dataSource.getConnection().prepareStatement(Queries.GET_TOTAL_CALORIE_CONSUMED + " where userID = " + userID
					+ " and foodLogDate = '" + date + "'").executeQuery();
			log.info("GET SUM CALORIES CONSUMED: Data fetched from database successfully");
			if (resultSet.next())
				totalCaloriesConsumed = resultSet.getInt("weightConsumed");
		} catch (SQLException e) {
			log.error("GET SUM CALORIES CONSUMED: SQL Exception occured while fetching user details");
		}
		finally{
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					log.error("GET SUM CALORIES BURNT: Result set object is not closed");
				}
			}
		}

		return totalCaloriesConsumed;
	}

	@Override
	public int getTargetWeight(int userID) {
		
		int dailyCalories = 0;
		ResultSet resultSet = null;
		log.info("Fetching target weight for a day");
		try {
			resultSet = dataSource.getConnection().prepareStatement(Queries.GET_DAILY_CALORIES + " where userID = " + userID).executeQuery();
			log.info("GET TARGET WEIGHT: Data fetched from database successfully");
			if (resultSet.next())
				dailyCalories = resultSet.getInt("dailyCalories");
		} catch (SQLException e) {
			log.error("GET TARGET WEIGHT: SQL Exception occured while fetching user details");
		}
		finally{
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					log.error("GET SUM CALORIES BURNT: Result set object is not closed");
				}
			}
		}

		return dailyCalories;
	}

}
