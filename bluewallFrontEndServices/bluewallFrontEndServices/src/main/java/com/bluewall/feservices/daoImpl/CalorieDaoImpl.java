package com.bluewall.feservices.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	 
	@SuppressWarnings("deprecation")
	@Override
	public int getSumCaloriesBurnt(int userID) {
		 
		int totalCaloriesBurnt = 0;
		log.info("Fetching Sum of Caloreies Burnt for a day");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		Date date = new Date();
		String currentDateTime = dateFormat.format(date);
		date.setHours(00);
		date.setMinutes(00);
		date.setSeconds(00);
		String startDateTime = dateFormat.format(date);
		
		try (ResultSet resultSet = dataSource.getConnection().prepareStatement(Queries.GET_TOTAL_CALORIE_BURNT + " where startTime >= '"+startDateTime+"' and startTime <= '"+currentDateTime+"'").executeQuery()){
			if(resultSet.next())
				totalCaloriesBurnt = resultSet.getInt("caloriesBurnt");
		}
		catch (SQLException e) {
			log.error("SQL Exception occured while fetching user details");
		}
		
		return totalCaloriesBurnt;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getSumCaloriesConsumed(int userID) {
		
		int totalCaloriesConsumed = 0;

		log.info("Fetching Sum of Caloreies Consumed for a day");
		
		/*Calculating Start time and current time of the date*/
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		Date date = new Date();
		String currentDateTime = dateFormat.format(date);
		date.setHours(00);
		date.setMinutes(00);
		date.setSeconds(00);
		
		String startDateTime = dateFormat.format(date);
		
		try (ResultSet resultSet = dataSource.getConnection().prepareStatement(Queries.GET_TOTAL_CALORIE_BURNT +  " where startTime >= '"+startDateTime+"' and startTime <= '"+currentDateTime+"'").executeQuery()) {
			if(resultSet.next())
				totalCaloriesConsumed = resultSet.getInt("weightConsumed");
		} catch (SQLException e) {
			log.error("SQL Exception occured while fetching user details");
		}
		
		return totalCaloriesConsumed;
	}

}
