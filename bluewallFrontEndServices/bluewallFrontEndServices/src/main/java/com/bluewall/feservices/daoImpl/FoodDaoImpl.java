package com.bluewall.feservices.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bluewall.feservices.dao.FoodDao;
import com.bluewall.feservices.util.Queries;
import com.bluewall.util.bean.UserFood;


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
	public List<UserFood> getUserFoodLog(int userID) {
		
		log.info("Now fetching food log for user id: " + userID);
		
		List<UserFood>  userFoodLogList = new ArrayList<UserFood>();
		ResultSet rs = null;
		
		try {
			rs = dataSource.getConnection().prepareStatement("select FoodInfo.name, FoodLog.type,"
					+ " FoodLog.weightConsumed, FoodLog.timeConsumed, FoodLog.calories"
					+ " from FoodLog, FoodInfo"
					+ " where FoodInfo.foodID = FoodLog.foodID and FoodLog.userID = " + userID)
					.executeQuery();
			
			while (rs.next()){
				UserFood userFoodLog = new UserFood();
				userFoodLog.setName(rs.getString("name"));
				userFoodLog.setType(rs.getString("type"));
				userFoodLog.setTimeConsumed(rs.getTimestamp("timeConsumed"));
				userFoodLog.setCalories(rs.getFloat("calories"));
				userFoodLog.setWeightConsumed(rs.getFloat("weightConsumed"));
				userFoodLogList.add(userFoodLog);
			}
			
		} catch (SQLException e) {
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

	@Override
	public void createFoodPlate(UserFood createFood, int userID) {
		
		int foodID;
		ResultSet rs = null;
		//Connection conn = null;
		String sqlStatement = "insert into FoodInfo(name, category, manufacturer)"
						+ " values('"+createFood.getName()+"', '"
						+ createFood.getCategory()+"', '" + createFood.getManufacturer() + "')";
		
		
		try {
			//conn = dataSource.getConnection();
			//conn.setAutoCommit(false);
			int rowUpdated = dataSource.getConnection().prepareStatement(sqlStatement).executeUpdate();
			
			if (rowUpdated != 0){
				log.info("Food plate created, Data inserted in ActivityInfo!");
			}
			else{
				log.info("Fail to create food plate for the user");
			}
			
			rs = dataSource.getConnection().prepareStatement("select max(foodId) as id from FoodInfo").executeQuery();
			
			if (rs.next()){
				foodID = rs.getInt("id");
				log.info("New food created with food id: "+foodID);
				
				PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(Queries.INS_USER_FOOD_LOG);
				preparedStatement.setInt(1, userID);
				preparedStatement.setString(2, createFood.getType());
				preparedStatement.setInt(3, foodID);
				preparedStatement.setFloat(4, createFood.getWeightConsumed());
				preparedStatement.setTimestamp(5, createFood.getTimeConsumed());
				preparedStatement.setInt(6, 14);
				preparedStatement.setFloat(7, createFood.getCalories());
				preparedStatement.setTimestamp(8, createFood.getFoodLogTime());
				preparedStatement.executeUpdate();
				//conn.commit();
				log.info("Food plate ready for user ID: " + userID);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("Create Food Service: SQL Exception");
		} 
		finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					log.info("Create Food Service: Result set object is not closed.");
				}
			}
			/* if (conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
		}
		
	}

}
