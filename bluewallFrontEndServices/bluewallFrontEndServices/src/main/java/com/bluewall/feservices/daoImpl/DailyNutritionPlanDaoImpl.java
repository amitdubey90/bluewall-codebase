package com.bluewall.feservices.daoImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bluewall.feservices.dao.DailyNutritionPlanDao;
import com.bluewall.feservices.util.Queries;
import com.bluewall.util.bean.UserDailyNutritionPlan;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Repository
public class DailyNutritionPlanDaoImpl implements DailyNutritionPlanDao{
	
	@Autowired
	DataSource datasource;
	
	@Override
	public UserDailyNutritionPlan getDailyNutritionPlan(int userID) {
		
		ResultSet rs = null;
		Connection connection = null;

		UserDailyNutritionPlan userDailyPlan = new UserDailyNutritionPlan();
		try {
			connection = datasource.getConnection();
			rs = connection.prepareStatement(Queries.GET_DAILY_NUTRITION_PLAN + " where userID = " + userID).executeQuery();
			
			while (rs.next()){
				userDailyPlan.setDailyCalories(rs.getDouble("dailyCalories"));
				userDailyPlan.setFatInGms(rs.getDouble("fatInGms"));
				userDailyPlan.setFatInCalories(rs.getDouble("fatInCalories"));
				userDailyPlan.setCarbInGms(rs.getDouble("carbsInGms"));
				userDailyPlan.setCarbInCalories(rs.getDouble("carbsInCalories"));
				userDailyPlan.setProteinInGms(rs.getDouble("proteinInGms"));
				userDailyPlan.setProteinInCalories(rs.getDouble("proteinInCalories"));
			}
			
		} catch (SQLException e) {
			log.error("GET DAILY NUTRITION PLAN: SQL Exception - Check the sql query or the connection string");
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error("GET DAILY NUTRITION PLAN: Could not close result set object");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error("GET DAILY NUTRITION PLAN: Error closing connection object " + e.getMessage());
				}
			}
		}
		
		return userDailyPlan;	
		
	}
	

}
