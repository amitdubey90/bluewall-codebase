package com.bluewall.feservices.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bluewall.feservices.bean.FoodInfo;
import com.bluewall.feservices.dao.FoodDao;
import com.bluewall.feservices.util.Queries;
import com.bluewall.util.bean.UserFood;

@Slf4j
@Repository
public class FoodDaoImpl implements FoodDao {

	@Autowired
	DataSource dataSource;

	/*
	 * Returns list of user food log object for a given user id
	 * 
	 * @see com.bluewall.feservices.dao.FoodDao#getUserFoodLog(int)
	 */

	@Override
	public List<UserFood> getUserFoodLog(int userID) {

		log.info("Now fetching food log for user id: " + userID);

		List<UserFood> userFoodLogList = new ArrayList<UserFood>();
		ResultSet rs = null;
		try {
			rs = dataSource.getConnection()
					.prepareStatement("select FoodInfo.name, FoodLog.type,"
							+ " FoodLog.weightConsumed, FoodLog.foodLogDate, FoodLog.calories, FoodLog.logTime"
							+ " from FoodLog, FoodInfo"
							+ " where FoodInfo.foodID = FoodLog.foodID and FoodLog.userID = " + userID + " order by FoodLog.logTime desc")
					.executeQuery();

			while (rs.next()) {
				UserFood userFoodLog = new UserFood();
				userFoodLog.setName(rs.getString("name"));
				userFoodLog.setType(rs.getString("type"));
				userFoodLog.setFoodLogDate(rs.getDate("foodLogDate"));
				userFoodLog.setCalories(rs.getFloat("calories"));
				userFoodLog.setWeightConsumed(rs.getFloat("weightConsumed"));
				userFoodLog.setLogTime(rs.getTimestamp("logTime"));
				userFoodLogList.add(userFoodLog);
			}

		} catch (SQLException e) {
			log.error("GET USER FOOD LOG: SQL Exception - Check the sql query or the connection string");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.error("GET USER FOOD LOG: Could not close result set object");
				}
			}
		}

		return userFoodLogList;

	}

	@Override
	public void createFoodPlate(UserFood createFood, int userID) {

		int foodID;
		ResultSet rs = null;
		Connection connection = null;

		String sqlStatement = Queries.GET_FOODID + " where name = '" + createFood.getName() + "'";

		try {
			connection = dataSource.getConnection();

			rs = connection.prepareStatement(sqlStatement).executeQuery();

			if (rs.next()) {
				foodID = rs.getInt("foodId");
				log.info("Food id for " + createFood.getName() + " : " + foodID);

				PreparedStatement preparedStatement = connection.prepareStatement(Queries.INS_USER_FOOD_LOG);
				preparedStatement.setInt(1, userID);
				preparedStatement.setString(2, createFood.getType());
				preparedStatement.setInt(3, foodID);
				preparedStatement.setFloat(4, createFood.getWeightConsumed());
				// TODO: put a check to identify user's device
				preparedStatement.setInt(5, 14);
				preparedStatement.setFloat(6, createFood.getCalories());
				preparedStatement.setTimestamp(7, createFood.getLogTime());
				preparedStatement.setDate(8, createFood.getFoodLogDate());
				preparedStatement.executeUpdate();

				log.info("Food logged for user ID: " + userID);
			}

		} catch (SQLException e) {
			log.error("Create Food Service:: Could not rollback updates " + e.getMessage());

		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error("Create Food Service: Result set object is not closed.");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.error("Create Food Service: Error closing connection object " + e.getMessage());
				}
			}
		}
	}

	@Override
	public List<FoodInfo> getFoodInfo(String foodName) {
		List<FoodInfo> foodList = new ArrayList<FoodInfo>();
		ResultSet rs = null;
		try {

			PreparedStatement pst = dataSource.getConnection().prepareStatement(
					"select f1.foodId, f1.name, f2.energy from FoodInfo f1 , FoodNutrientPer100Gram f2 where f1.name like ? and f1.foodId=f2.foodId;");
			pst.setString(1, foodName);
			rs = pst.executeQuery();

			while (rs.next()) {
				FoodInfo info = new FoodInfo();
				info.setFoodName(rs.getString("name"));
				info.setFoodId(rs.getInt("foodId"));
				info.setFoodCalorie(rs.getDouble("energy"));
				foodList.add(info);
			}

		} catch (SQLException e) {
			log.error("GET FOOD INFO: SQL Exception - Check the sql query or the connection string");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error("GET FOOD INFO: Could not close result set object");
				}
			}
		}

		return foodList;
	}

	@Override
	public void rateFoodItems(int foodId, int foodRating, int userID) {
		
		ResultSet rs = null;
		
		try{
			PreparedStatement prepStat = dataSource.getConnection().prepareStatement(Queries.UPSERT_USER_RATINGS);
			prepStat.setInt(1, userID);
			prepStat.setInt(2, foodId);
			prepStat.setInt(3, foodRating);
			prepStat.executeUpdate();
			prepStat.close();
			log.info("RATE FOOD ITEMS: Ratings successfully logged by user id: " + userID);
		}
		 catch (SQLException e) {
				log.error("RATE FOOD ITEMS: SQL Exception - Check the sql query or the connection string");
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error("RATE FOOD ITEMS - Could not close result set object");
				}
			}
		}
	}
}
