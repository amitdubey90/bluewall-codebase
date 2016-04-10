package com.bluewall.feservices.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

		int totalCaloriesBurnt = 0;
		log.info("Fetching Sum of Caloreies Burnt for a day");
		/*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar calendar = Calendar.getInstance();*/
		String currentDateTime = date + " 23:59:59";
		System.out.println("currentDateTime: " + currentDateTime);
		/*try {
			calendar.setTime(dateFormat.parse(currentDateTime));
		} catch (ParseException e1) {
			log.error("Exception occured while parsing Date",e1);
		}*/
		/*calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);*/
		String startDateTime = date + " 00:00:00";
		System.out.println("SQL: " + Queries.GET_TOTAL_CALORIE_BURNT + " where userID = " + userID	+ " and startTime >= '" + startDateTime + "' and startTime <= '" + currentDateTime + "'");
		try (ResultSet resultSet = dataSource.getConnection().prepareStatement(Queries.GET_TOTAL_CALORIE_BURNT + " where userID = " + userID
						+ " and startTime >= '" + startDateTime + "' and startTime <= '" + currentDateTime + "'").executeQuery()) {
			log.info("Data fetched from database successfully");
			if (resultSet.next())
				totalCaloriesBurnt = resultSet.getInt("caloriesBurnt");
		} catch (SQLException e) {
			log.error("SQL Exception occured while fetching user details");
		}

		return totalCaloriesBurnt;
	}

	@Override
	public int getSumCaloriesConsumed(int userID, String date) {

		int totalCaloriesConsumed = 0;

		log.info("Fetching Sum of Caloreies Consumed for a day");
		/*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();*/
		String currentDateTime = date + " 23:59:59";
		/*try {
			calendar.setTime(dateFormat.parse(currentDateTime));
		} catch (ParseException e1) {
			log.error("Exception occured while parsing Date",e1);
		}*/
		/*calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);*/
		String startDateTime = date + " 00:00:00";
		System.out.println("SQL: " + Queries.GET_TOTAL_CALORIE_CONSUMED + " where userID = " + userID + " and logTime >= '" + startDateTime + "' and logTime <= '" + currentDateTime + "'");
		try (ResultSet resultSet = dataSource.getConnection().prepareStatement(Queries.GET_TOTAL_CALORIE_CONSUMED + " where userID = " + userID
						+ " and logTime >= '" + startDateTime + "' and logTime <= '" + currentDateTime + "'").executeQuery()) {
			log.info("Data fetched from database successfully");
			if (resultSet.next())
				totalCaloriesConsumed = resultSet.getInt("weightConsumed");
		} catch (SQLException e) {
			log.error("SQL Exception occured while fetching user details");
		}

		return totalCaloriesConsumed;
	}

}
