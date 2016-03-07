package com.bluewall.feservices.daoImpl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.dao.UserDao;
import com.bluewall.util.bean.UserCredential;
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
	
	@Override
	public UserCredential getUserConnectionCredsById(int userId,int providerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUserCredentials(UserCredential newUserCreds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUserCredentials(UserCredential newUserCreds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createUser(UserProfile user) {
		int userID;
		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp loginTime = new java.sql.Timestamp(calendar.getTime().getTime());
		
		String insrtUserInfosql = "insert into UserInfo(firstName, lastName, emailID, contactNumber, age, gender, height,"
				+ " weight, activityLevel, currentLocation) "
				+ "values ('"+user.getFirstName()+"', '"+user.getLastName()+"','"+user.getEmailID()+"'," 
				+ user.getContactNumber()+","+user.getAge()+",'"+user.getGender()+"',"+user.getHeight()
				+ "," + user.getWeight() + ",'"+user.getActivityLevel()+"','"+user.getCurrentLocation()+"')";
		
		try {
			int rowCount = datasource.getConnection().prepareStatement(insrtUserInfosql).executeUpdate();
			System.out.println("data in userinfo detail: "+rowCount);
			
			if (rowCount != 0){
				log.info("User succesfully registered, Data inserted in UserInfo!");
			}
			else{
				log.info("Fail to register the user");
			}
			
			ResultSet rs = datasource.getConnection().prepareStatement("select userID from UserInfo where emailID ="
					+ "'"+user.getEmailID()+"'").executeQuery();
			
			if (rs.next()){
				userID = rs.getInt("userID");
				log.info("New user registered with user id: "+userID);
				
				int count = datasource.getConnection().prepareStatement("insert into UserGoal(userID, goalType, targetWeight, "
						+ "startDate, endDate) values("+userID+",'"+user.getGoalType()+"',"+user.getTargetWeight()+","
								+ user.getStartDate() +","+user.getEndDate()+")").executeUpdate();
				System.out.println("data in goal detail: "+count);
				
				if (count != 0){
					log.info("New user's goal information inserted successfully, Data inserted in UserGoal!");
				}
				else
					log.info("Unable to add new user's goal info");
				
				String insLoginDetailsql = "insert into LoginDetail values(?,?,?,?)";
				PreparedStatement prepStatement = datasource.getConnection().prepareStatement(insLoginDetailsql);
				prepStatement.setString(1, user.getEmailID());
				prepStatement.setString(2, user.getPasswd());
				prepStatement.setTimestamp(3, loginTime);
				prepStatement.setInt(4, userID);
				int rowUpdateCount = prepStatement.executeUpdate();
				System.out.println("data in login detail: "+rowUpdateCount);
			}
			else{
				log.info("Unable to fetch registered user");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
	}

}
