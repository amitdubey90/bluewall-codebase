package com.bluewall.feservices.daoImpl;

import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.feservices.dao.UserDao;
import com.bluewall.feservices.util.Queries;
import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.bean.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@Component
@Service
@Repository("userDao")

@Slf4j

public class UserDaoImpl implements UserDao {
	@Autowired
	DataSource datasource;

	@Override
	public void createUser(UserProfile user) {

		int userID;
		ResultSet rs = null;
		Connection connection = null;
		
		String insrtUserInfosql = "insert into UserInfo(firstName, lastName, emailID, contactNumber, age, gender, height,"
				+ " weight, activityLevel, currentLocation) "
				+ "values ('"+user.getFirstName()+"', '"+user.getLastName()+"','"+user.getEmailID()+"'," 
				+ user.getContactNumber()+","+user.getAge()+",'"+user.getGender()+"',"+user.getHeight()
				+ "," + user.getWeight() + ",'"+user.getActivityLevel()+"','"+user.getCurrentLocation()+"')";

		try {
			connection = datasource.getConnection();
			
			connection.setAutoCommit(false);
			
			int rowCount = connection.prepareStatement(insrtUserInfosql).executeUpdate();
			System.out.println("data in userinfo detail");
			
			if (rowCount != 0){
				log.info("User succesfully registered, Data inserted in UserInfo!");
			}
			else{
				log.info("Fail to register the user");
			}
			
			rs = connection.prepareStatement("select userID from UserInfo where emailID = "
					+ "'"+user.getEmailID()+"'")
					.executeQuery();
			
			if (rs.next()){
				userID = rs.getInt("userID");
				log.info("New user registered with user id: "+userID);
				
				//check to see if user enters a goal
				if (user.getGoalType() != null){
					
					PreparedStatement preparedStatement = connection.prepareStatement(Queries.INS_USER_GOALS);
					preparedStatement.setInt(1, userID);
					preparedStatement.setString(2, user.getGoalType());
					preparedStatement.setDouble(3, user.getTargetWeight());
					preparedStatement.setTimestamp(4, user.getStartDate());
					preparedStatement.setTimestamp(5, user.getEndDate());
					preparedStatement.executeUpdate();
					log.info("User goals inserted");

				}
								
				PreparedStatement prepStatement = connection.prepareStatement(Queries.INS_USERS);
				prepStatement.setString(1, user.getEmailID());
				prepStatement.setString(2, user.getPassword());
				prepStatement.executeUpdate();
				connection.commit();
			}
			else{
				log.info("Unable to fetch registered user");
			}
			
		} catch (SQLException e) {
			try {
				connection.rollback();
				log.info("Create User Service: Successfully rolled back changes from the database!");
			  } catch (SQLException e1) {
				  log.info("Create User Service: Could not rollback updates " + e1.getMessage());
			  }
		} 
		
		finally {
			if (rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.info("CREATE USER SERVICE: Result set object is not closed.");
				}
			}
			if (connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					log.info("Create Activity Service: Error closing connection object " + e.getMessage());
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
			if (rs.next()) {
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");

				int userId = rs.getInt("userId");

				userPrincipal = new UserPrincipal(emailID,firstName,
						lastName, userId);

			}
			log.info("loadUserByName successful");
		} catch (SQLException e) {
			log.error("SqlException in loadUserByName {}", e);
		}
		return userPrincipal;
	}

}
