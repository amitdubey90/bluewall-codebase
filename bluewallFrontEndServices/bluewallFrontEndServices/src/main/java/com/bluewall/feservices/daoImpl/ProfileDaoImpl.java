package com.bluewall.feservices.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bluewall.feservices.dao.ProfileDao;
import com.bluewall.feservices.util.DatabaseResouceCloser;
import com.bluewall.feservices.util.Queries;
import com.bluewall.util.bean.UserProfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ProfileDaoImpl implements ProfileDao{
	
	@Autowired
	DataSource dataSource;

	@Override
	public UserProfile getUserProfile(int userID) {
		
		log.info("Fetching user profile");
		
		UserProfile userProfile = new UserProfile();
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement pst = null;
		
		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(Queries.GET_USER_PROFILE + " where UserInfo.userID = UserGoal.userID and UserInfo.userID = " + userID);
			rs = pst.executeQuery();
			
			while (rs.next()){
				userProfile.setFirstName(rs.getString("firstName"));
				userProfile.setLastName(rs.getString("lastName"));
				userProfile.setEmailID(rs.getString("emailID"));
				userProfile.setContactNumber(rs.getString("contactNumber"));
				userProfile.setAge(rs.getInt("age"));
				userProfile.setGender(rs.getString("gender"));
				userProfile.setHeight(rs.getFloat("height"));
				userProfile.setWeight(rs.getFloat("weight"));
				userProfile.setActivityLevel(rs.getString("activityLevel"));
				userProfile.setGoalType(rs.getString("goalType"));
				userProfile.setTargetWeight(rs.getFloat("targetWeight"));
				userProfile.setStartDate(rs.getDate("startDate"));
				userProfile.setEndDate(rs.getDate("endDate"));
			}
		}
		
		catch (SQLException e) {
			log.info("GET USER PROFILE DATA: SQL Exception - Check the sql query or the connection string");
			e.printStackTrace();
		} finally {
			DatabaseResouceCloser.closeAllSilent(connection, pst, rs);
		}
		
		return userProfile;
	}

	@Override
	public void updateUserProfile(UserProfile userProfile, int userId) {
		log.info("Updating user profile");
		
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement pst = null;
		
		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(Queries.UPDATE_USER_PROFILE + " where UserInfo.userID = " + userId + " and UserGoal.userID = " + userId);
			pst.setFloat(1, userProfile.getHeight());
			pst.setFloat(2, userProfile.getWeight());
			pst.setString(3, userProfile.getActivityLevel());
			pst.setFloat(4, userProfile.getTargetWeight());
			pst.setString(5, userProfile.getGoalType());
			pst.executeUpdate();
			log.info("UPDATE USER PROFILE: Update Successful");
		}
		catch (SQLException e) {
			log.info("UPDATE USER PROFILE DATA: SQL Exception - Check the sql query or the connection string");
			e.printStackTrace();
		} finally {
			DatabaseResouceCloser.closeAllSilent(connection, pst, rs);
		}
	}
}
