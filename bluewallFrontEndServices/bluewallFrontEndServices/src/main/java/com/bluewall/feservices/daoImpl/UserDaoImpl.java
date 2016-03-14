package com.bluewall.feservices.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.dao.UserDao;
import com.bluewall.feservices.util.Queries;
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
	public UserCredential getUserConnectionCredsById(int userId, int providerId) {
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
		ResultSet rs =null;
		
		String insrtUserInfosql = "insert into UserInfo(firstName, lastName, emailID, contactNumber, age, gender, height,"
				+ " weight, activityLevel, currentLocation) "
				+ "values ('"+user.getFirstName()+"', '"+user.getLastName()+"','"+user.getEmailID()+"'," 
				+ user.getContactNumber()+","+user.getAge()+",'"+user.getGender()+"',"+user.getHeight()
				+ "," + user.getWeight() + ",'"+user.getActivityLevel()+"','"+user.getCurrentLocation()+"')";
		
		//Connection conn;

		try {
			//conn = datasource.getConnection();
			//conn.setAutoCommit(false);
			
			int rowCount = datasource.getConnection().prepareStatement(insrtUserInfosql).executeUpdate();
			System.out.println("data in userinfo detail: "+rowCount);
			
			if (rowCount != 0){
				log.info("User succesfully registered, Data inserted in UserInfo!");
			}
			else{
				log.info("Fail to register the user");
			}
			
			rs = datasource.getConnection().prepareStatement("select userID from UserInfo where emailID ="
					+ "'"+user.getEmailID()+"'").executeQuery();
			
			if (rs.next()){
				userID = rs.getInt("userID");
				log.info("New user registered with user id: "+userID);
				
				int count = datasource.getConnection().prepareStatement("insert into UserGoal(userID, goalType, targetWeight,startDate, endDate) values ("+userID+",'"+user.getGoalType()+"',"+user.getTargetWeight()+","
								+ user.getStartDate() +","+user.getEndDate()+")").executeUpdate();
				
				System.out.println("data in goal detail: "+count);
				
				if (count != 0){
					log.info("New user's goal information inserted successfully, Data inserted in UserGoal!");
				}
				else
					log.info("Unable to add new user's goal info");
				
				PreparedStatement prepStatement = datasource.getConnection().prepareStatement(Queries.INS_USERS);
				prepStatement.setString(1, user.getEmailID());
				prepStatement.setString(2, user.getPassword());
				prepStatement.executeUpdate();
				//conn.commit();
			}
			else{
				log.info("Unable to fetch registered user");
			}
			
		} catch (SQLException e) {
			log.info("CREATE USER SERVICE: SQL Exception");
		} finally {
			if (rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.info("CREATE USER SERVICE: Result set object is not closed.");
				}
			}
		}
		
	}

}
