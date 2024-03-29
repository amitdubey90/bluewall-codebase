package com.bluewall.feservices.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.bluewall.feservices.util.DatabaseResouceCloser;
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
		PreparedStatement pst = null;
		Connection connection = null;

		log.info("Now fetching user activity logs");

		try {
			connection = dataSource.getConnection();
			pst = connection
					.prepareStatement("select ActivityLog.name, ActivityLog.distance,ActivityLog.duration, "
							+ "ActivityLog.caloriesBurnt,ActivityLog.activityLogDate, SupportedDevice.deviceName "
							+ "from ActivityLog, SupportedDevice where  ActivityLog.userID = " + userID
							+ " and SupportedDevice.deviceID = ActivityLog.loggedFrom order by ActivityLog.activityLogDate desc");
			rs = pst.executeQuery();

			while (rs.next()) {
				UserActivityLog userActivity = new UserActivityLog();
//				userActivity.setDistance(rs.getFloat("distance"));
				userActivity.setDuration(rs.getFloat("duration"));
				userActivity.setCaloriesBurnt(rs.getFloat("caloriesBurnt"));
				userActivity.setActivityLogDate(rs.getDate("activityLogDate"));
				userActivity.setLoggedFrom(rs.getString("deviceName"));
				
				if(rs.getString("deviceName").equalsIgnoreCase("Fitbit") || rs.getString("deviceName").equalsIgnoreCase("Jawbone")){
					userActivity.setName(rs.getString("deviceName"));
				}
				else{
					userActivity.setName(rs.getString("name"));
				}
				
				userActivityLog.add(userActivity);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("GET USER ACTIVITY SERVICE: SQL Exception.");
		} finally {
			DatabaseResouceCloser.closeAllSilent(connection, pst, rs);
		}

		return userActivityLog;
	}

	@Override
	public UserCredential getUserDeviceInfo(int userId) {

		UserCredential creds = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			pst = connection.prepareStatement(SQLQueries.GET_USER_DEVICE_CREDENTIALS);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			while (rs.next()) {
				creds = new UserCredential();
				creds.setDeviceID(rs.getInt("deviceId"));
				creds.setAccessToken(rs.getString("accessToken"));
			}
		} catch (SQLException sqlExp) {
			log.error("GET USER DEVICE INFO: SQL Exception while fetching user device Info");
		}
		finally{
			DatabaseResouceCloser.closeAllSilent(connection, pst, rs);
		}
		return creds;
	}

	@Override
	public void createActivity(UserActivityLog userActivity, int userId) {

		Connection connection = null;
		PreparedStatement prepStatement = null;

		try {
			connection = dataSource.getConnection();

			connection.setAutoCommit(false);

			prepStatement = connection.prepareStatement(Queries.INS_USER_ACTIVITY_LOG);

			prepStatement.setInt(1, userId);
			prepStatement.setString(2, userActivity.getName());
			prepStatement.setFloat(3, 0);
			prepStatement.setDate(4, userActivity.getActivityLogDate());
			prepStatement.setFloat(5, userActivity.getDuration());
			prepStatement.setFloat(6, userActivity.getCaloriesBurnt());
			prepStatement.setInt(7, 14);
			prepStatement.setTimestamp(8, userActivity.getLogTime());
			prepStatement.executeUpdate();
			connection.commit();
			log.info("Activity Successfully created for user ID: " + userId);

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Create Activity Service: Successfully rolled back changes from the database!");
		} finally {
			DatabaseResouceCloser.closeAllSilent(connection, prepStatement, null);
		}
	}
}
