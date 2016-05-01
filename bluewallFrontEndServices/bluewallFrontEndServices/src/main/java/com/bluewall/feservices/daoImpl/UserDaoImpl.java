package com.bluewall.feservices.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.feservices.dao.UserDao;
import com.bluewall.feservices.util.Queries;
import com.bluewall.util.bean.FoodRating;
import com.bluewall.util.bean.UserDailyNutritionPlan;
import com.bluewall.util.bean.UserProfile;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Component
@Service
@Repository("userDao")

@Slf4j

public class UserDaoImpl implements UserDao {
	@Autowired
	DataSource datasource;

	/*
	 * @Override public UserCredential getUserConnectionCredsById(int userId,
	 * int providerId) { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public void updateUserCredentials(UserCredential newUserCreds)
	 * { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void insertUserCredentials(UserCredential newUserCreds)
	 * { // TODO Auto-generated method stub
	 * 
	 * }
	 */

	@Override
	public int createUser(UserProfile user) {

		int userID = 0;
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = datasource.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(Queries.INS_USER_INFO);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getEmailID());
			preparedStatement.setString(4, user.getContactNumber());
			preparedStatement.setInt(5, user.getAge());
			preparedStatement.setString(6, user.getGender());
			preparedStatement.setDouble(7, user.getHeight());
			preparedStatement.setDouble(8, user.getWeight());
			preparedStatement.setString(9, user.getActivityLevel());

			preparedStatement.setString(10, user.getCurrentLocation());
			int rowCount = preparedStatement.executeUpdate();
			preparedStatement.close();

			if (rowCount != 0) {
				log.info("CREATE USER SERVICE: User succesfully registered, Data inserted in UserInfo!");
			} else {
				log.info("CREATE USER SERVICE: Fail to register the user");
			}

			rs = connection
					.prepareStatement("select userID from UserInfo where emailID = " + "'" + user.getEmailID() + "'")
					.executeQuery();

			if (rs.next()) {
				userID = rs.getInt("userID");
				log.info("CREATE USER SERVICE: New user registered with user id: " + userID);
				PreparedStatement prepStatement = connection.prepareStatement(Queries.INS_USER_TASTE_PREFERENCES);
				for (FoodRating foodRating : user.getFoodTasteList()) {
					prepStatement.setInt(1, userID);
					prepStatement.setInt(2, foodRating.getKey());
					prepStatement.setInt(3, foodRating.getValue());
					prepStatement.setTimestamp(4, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
					prepStatement.executeUpdate();

				}
				log.info("CREATE USER SERVICE: Taste preferences  saved succeefully for user id: " + userID);

				// check to see if user enters a goal
				if (user.getGoalType() != null) {

					preparedStatement = connection.prepareStatement(Queries.INS_USER_GOALS);
					preparedStatement.setInt(1, userID);
					preparedStatement.setString(2, user.getGoalType());
					preparedStatement.setFloat(3, user.getTargetWeight());
					preparedStatement.setDate(4, user.getStartDate());
					preparedStatement.setDate(5, user.getEndDate());
					preparedStatement.executeUpdate();
					preparedStatement.close();
					log.info("CREATE USER SERVICE: User Goals inserted");

				}

				log.info("CREATE USER SERVICE: Now inserting in users database");
				
				preparedStatement = connection.prepareStatement(Queries.INS_USERS);
				preparedStatement.setString(1, user.getEmailID());
				preparedStatement.setString(2, user.getPassword());
				preparedStatement.executeUpdate();
				preparedStatement.close();
				connection.commit();
			} else {
				log.info("CREATE USER SERVICE: Unable to fetch registered user");
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
				log.error("CREATE USER SERVICE: Successfully rolled back changes from the database!");
				e.printStackTrace();
			} catch (SQLException e1) {
				log.error("CREATE USER SERVICE: Could not rollback updates " + e1.getMessage());
			}
		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error("CREATE USER SERVICE: Result set object is not closed.");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error("CREATE USER SERVICE: Error closing connection object " + e.getMessage());
				}
			}
		}

		return userID;
	}

	@Override
	public List<UserProfile> getUserDetails(int userID) {

		ResultSet rs = null;
		Connection connection = null;
		List<UserProfile> userProfileList = new ArrayList<UserProfile>();

		try {
			connection = datasource.getConnection();
			rs = connection.prepareStatement(Queries.GET_USER_INFO + " where userID = " + userID).executeQuery();

			while (rs.next()) {
				UserProfile userProfile = new UserProfile();
				userProfile.setFirstName(rs.getString("firstName"));
				userProfile.setLastName(rs.getString("lastName"));
				userProfile.setEmailID(rs.getString("emailID"));
				userProfile.setContactNumber(rs.getString("contactNumber"));
				userProfile.setAge(rs.getInt("age"));
				userProfile.setGender(rs.getString("gender"));
				userProfile.setHeight(rs.getFloat("height"));
				userProfile.setWeight(rs.getFloat("weight"));
				userProfile.setActivityLevel(rs.getString("activityLevel"));
				userProfile.setCurrentLocation(rs.getString("currentLocation"));
				userProfileList.add(userProfile);
			}

		} catch (SQLException e) {
			log.error("GET USER DETAILS: SQL Exception - Check the sql query or the connection string");
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error("GET USER DETAILS: Could not close result set object");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error("GET USER DETAILS: Error closing connection object " + e.getMessage());
				}
			}
		}

		return userProfileList;

	}

	@Override
	public void createNutrientPlan(UserDailyNutritionPlan dailyPlan, int userID) {

		Connection connection = null;
		log.info("CREATE NUTRITION PLAN: Now inserting nutrition plan in database for user id: " + userID);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			connection = datasource.getConnection();
			PreparedStatement prepStatement = connection.prepareStatement(Queries.INS_DAILY_NUTRITION_PLAN);

			prepStatement.setInt(1, userID);
			prepStatement.setDouble(2, dailyPlan.getDailyCalories());
			prepStatement.setDouble(3, dailyPlan.getFatInGms());
			prepStatement.setDouble(4, dailyPlan.getFatInCalories());
			prepStatement.setDouble(5, dailyPlan.getCarbInGms());
			prepStatement.setDouble(6, dailyPlan.getCarbInCalories());
			prepStatement.setDouble(7, dailyPlan.getProteinInGms());
			prepStatement.setDouble(8, dailyPlan.getProteinInCalories());
			prepStatement.setString(9, dateFormat.format(new Date()));
			prepStatement.executeUpdate();
			prepStatement.close();
			log.info("CREATE NUTRITION PLAN: Nutrient plan saved succeefully for user id: " + userID);

		} catch (SQLException e) {
			log.error("CREATE NUTRIENT PLAN: SQL Exception - Check the sql query or the connection string");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error("CREATE NUTRIENT PLAN: Error closing connection object " + e.getMessage());
				}
			}
		}

	}

	@Override
	public UserPrincipal loadUserByName(String emailID) {

		log.info("loadUserByName started");
		UserPrincipal userPrincipal = null;

		try (PreparedStatement pst = datasource.getConnection().prepareStatement(Queries.GET_USER_PRINCIPAL)) {
			int colId = 1;

			pst.setString(colId++, emailID);

			ResultSet rs = pst.executeQuery();
			pst.close();
			
			if (rs.next()) {
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				int age = rs.getInt("age");
				float height = rs.getFloat("height");
				float weight = rs.getFloat("weight");

				int userId = rs.getInt("userId");

				userPrincipal = new UserPrincipal(emailID, firstName, lastName, userId, age, height, weight);

			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.info("RATE FOOD ITEMS - Could not close result set object");
				}
			}
			log.info("loadUserByName successful");
		} catch (SQLException e) {
			log.error("SqlException in loadUserByName {}", e);
		}
		return userPrincipal;
	}

	

}
