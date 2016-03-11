package com.bluewall.feservices.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bluewall.feservices.dao.UserActivityDao;
import com.bluewall.feservices.util.SQLQueries;
import com.bluewall.util.bean.UserCredential;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserActivityDaoImpl implements UserActivityDao {

	@Autowired
	DataSource dataSource;

	@Override
	public UserCredential getUserDeviceInfo(String userId) {

		
		UserCredential creds = null;
		try{
		PreparedStatement pst = dataSource.getConnection().prepareStatement(SQLQueries.GET_USER_DEVICE_CREDENTIALS);
		pst.setString(1, userId);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			creds = new UserCredential();
			creds.setDeviceID(rs.getInt("deviceId"));
			creds.setAccessToken(rs.getString("accessToken"));
		}}catch(SQLException sqlExp){
			log.error("SQL Exception while fetching user device Info");
		}
		return creds;

	}

}
