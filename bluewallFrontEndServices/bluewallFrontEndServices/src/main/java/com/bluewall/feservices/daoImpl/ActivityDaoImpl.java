package com.bluewall.feservices.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bluewall.feservices.dao.ActivityDao;
import com.bluewall.util.bean.UserActivityLog;

/**
 * @author Vrushank
 *
 */

@Slf4j
@Repository
public class ActivityDaoImpl implements ActivityDao {
	
	@Autowired
 	DataSource dataSource;
	
	List<UserActivityLog> userActivityList = new ArrayList<UserActivityLog>();
	
	/**
	 * @param userID
	 * @return List of UserActivityLog objects
	 */
	
	@Override
	public List<UserActivityLog> getUserActivityLogs(int userID) {
		
		List<UserActivityLog> userActivityLog = new ArrayList<UserActivityLog>();
		ResultSet rs = null;
		// TODO Auto-generated method stub
		log.info("Now fetching user activity logs");
		
		try {
			
			rs = dataSource.getConnection().prepareStatement("select ActivityLog.type, ActivityLog.distance, ActivityLog.startTime, "
					+ "ActivityLog.duration, ActivityLog.caloriesBurnt, ActivityInfo.name "
					+ "from ActivityLog, ActivityInfo where ActivityLog.userID = "+ userID + " and ActivityInfo.activityID = ActivityLog.activityID").executeQuery();
			
			while (rs.next()){
				UserActivityLog userActivity  = new UserActivityLog();
				userActivity.setName(rs.getString("name"));
				userActivity.setCaloriesBurnt(rs.getInt("caloriesBurnt"));
				userActivity.setDistance(rs.getFloat("distance"));
				userActivity.setDuration(rs.getInt("duration"));
				userActivity.setStartTime(rs.getTimestamp("startTime"));
				userActivity.setType(rs.getInt("type"));
				userActivityLog.add(userActivity);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("SQL Exception - Check your sql statement or connection string");
		} 
		finally{
			if (rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return userActivityLog;
	}
	
	
}	
