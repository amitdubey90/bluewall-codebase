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

import com.bluewall.feservices.dao.ActivityDao;
import com.bluewall.feservices.util.Queries;
import com.bluewall.feservices.util.SQLQueries;
import com.bluewall.util.bean.UserActivityLog;
import com.bluewall.util.bean.UserCredential;

/**
 * @author Vrushank
 *
 */

@Slf4j
@Repository
public class ActivityDaoImpl implements ActivityDao {

	@Autowired
	DataSource dataSource;

	/**
	 * @param userID
	 * @return List of UserActivityLog objects
	 */

	@Override
	public List<UserActivityLog> getUserActivityLogs(int userID) {

		List<UserActivityLog> userActivityLog = new ArrayList<UserActivityLog>();
		ResultSet rs = null;

		log.info("Now fetching user activity logs");

		try {

			rs = dataSource.getConnection()
					.prepareStatement("select ActivityInfo.type, ActivityLog.distance, ActivityLog.startTime, "
							+ "ActivityLog.duration, ActivityLog.caloriesBurnt, ActivityInfo.name, ActivityInfo.MET "
							+ "from ActivityLog, ActivityInfo where ActivityLog.userID = " + userID
							+ " and ActivityInfo.activityID = ActivityLog.activityID")
					.executeQuery();

			while (rs.next()) {
				UserActivityLog userActivity = new UserActivityLog();
				userActivity.setName(rs.getString("name"));
				userActivity.setCaloriesBurnt(rs.getInt("caloriesBurnt"));
				userActivity.setDistance(rs.getFloat("distance"));
				userActivity.setDuration(rs.getInt("duration"));
				userActivity.setStartTime(rs.getTimestamp("startTime"));
				userActivity.setType(rs.getString("type"));
				userActivity.setMET(rs.getString("MET"));
				userActivityLog.add(userActivity);
			}

		} catch (SQLException e) {
			log.info("GET USER ACTIVITY SERVICE: SQL Exception.");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.info("GET USER ACTIVITY SERVICE: Result set object is not closed");
				}
			}
		}

		return userActivityLog;
	}

	@Override
	public UserCredential getUserDeviceInfo(int userId) {

		UserCredential creds = null;
		try {
			PreparedStatement pst = dataSource.getConnection().prepareStatement(SQLQueries.GET_USER_DEVICE_CREDENTIALS);
			pst.setInt(1, userId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				creds = new UserCredential();
				creds.setDeviceID(rs.getInt("deviceId"));
				creds.setAccessToken(rs.getString("accessToken"));
			}
		} catch (SQLException sqlExp) {
			log.error("SQL Exception while fetching user device Info");
		}
		return creds;
	}

	@Override
	public void createActivity(UserActivityLog userActivity, int userId) {
		
		int activityID;
		ResultSet rs = null;
		//Connection conn = null;
		String sqlStatement = "insert into ActivityInfo(name, type, MET)"
				+ " values('"+userActivity.getName()+"', '"
				+userActivity.getType()+"', '" + userActivity.getMET() + "')";
		
		
		try {
			//conn = dataSource.getConnection();
			//conn.setAutoCommit(false);
			int rowUpdated = dataSource.getConnection().prepareStatement(sqlStatement).executeUpdate();
			
			if (rowUpdated != 0){
				log.info("Activity created, Data inserted in ActivityInfo!");
			}
			else{
				log.info("Fail to create activity for the user");
			}
			
			rs = dataSource.getConnection().prepareStatement("select max(activityID) as id from ActivityInfo").executeQuery();
			
			if (rs.next()){
				activityID = rs.getInt("id");
				log.info("New activity created with activity id: "+activityID);
				
				PreparedStatement ps = dataSource.getConnection().prepareStatement(Queries.INS_USER_ACTIVITY_LOG);
				ps.setInt(1, userId);
				ps.setFloat(2, userActivity.getDistance());
				ps.setTimestamp(3, userActivity.getStartTime());
				ps.setInt(4, 14);
				ps.setInt(5, userActivity.getDuration());
				ps.setInt(6, userActivity.getCaloriesBurnt());
				ps.setInt(7, activityID);
				ps.executeUpdate();
				//conn.commit();
				log.info("Activity Successfully creates for user ID: " + userId);
			}
			
			
		} catch (SQLException e) {
			log.info("Create Activty Service: SQL Exception");
		} 
		finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					log.info("Create Actvity Service: Result set object is not closed.");
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
