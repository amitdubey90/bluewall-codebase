package com.bluewall.feservices.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bluewall.feservices.dao.FoodDao;
import com.bluewall.util.bean.UserFoodLog;

@Slf4j
@Repository
public class FoodDaoImpl implements FoodDao{
	
	@Autowired
	DataSource dataSource;
	
	/*
	 * Returns list of user food log object for a given user id
	 * @see com.bluewall.feservices.dao.FoodDao#getUserFoodLog(int)
	 */
	
	@Override
	public List<UserFoodLog> getUserFoodLog(int userID) {
		
		log.info("Now fetching food log for user id: " + userID);
		
		List<UserFoodLog>  userFoodLogList = new ArrayList<UserFoodLog>();
		ResultSet rs = null;
		
		try {
			rs = dataSource.getConnection().prepareStatement("select FoodInfo.name, FoodLog.type,"
					+ " FoodLog.weightConsumed, FoodLog.timeConsumed, FoodLog.calories"
					+ " from FoodLog, FoodInfo"
					+ " where FoodInfo.foodID = FoodLog.foodID and FoodLog.userID = " + userID)
					.executeQuery();
			
			while (rs.next()){
				UserFoodLog userFoodLog = new UserFoodLog();
				userFoodLog.setName(rs.getString("name"));
				userFoodLog.setType(rs.getString("type"));
				userFoodLog.setTimeConsumed(rs.getTimestamp("timeConsumed"));
				userFoodLog.setCalories(rs.getFloat("calories"));
				userFoodLog.setWeightConsumed(rs.getFloat("weightConsumed"));
				userFoodLogList.add(userFoodLog);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("SQL Exception - Check the sql query or the connection string");
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.info("Could not close result set object");
				}
			}
		}
		
		return userFoodLogList;
			
	}

}
