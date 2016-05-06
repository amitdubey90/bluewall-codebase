package com.bluewall.feservices.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bluewall.feservices.util.SQLQueries;
import com.bluewall.util.bean.AccessCredentials;
import com.bluewall.util.bean.UserConnectedDevice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DeviceAuthorizationDaoImpl implements DeviceAuthorizationDaoIfc {

	@Autowired
	DataSource dataSource;

	public boolean storeUserAccessToken(AccessCredentials credentials) {
		log.info("storeUserAccessToken started");

		try (PreparedStatement pst = dataSource.getConnection().prepareStatement(SQLQueries.INSERT_USER_ACCESS_TOKEN)) {
			int colId = 1;

			pst.setInt(colId++, credentials.getUserId());
			pst.setInt(colId++, credentials.getDeviceId());
			pst.setString(colId++, credentials.getAccessToken());
			pst.setString(colId++, credentials.getRefreshToken());
			pst.setTimestamp(colId++, credentials.getExpirationTime());

			pst.execute();
			log.info("storeUserAccessToken successful");
			return true;
		} catch (SQLException e) {
			log.error("SqlException in storeUserAccessToken {}", e);
		}
		return false;
	}

	@Override

	public UserConnectedDevice checkIfUserHasDevice(int userID) {
		UserConnectedDevice device = UserConnectedDevice.builder().build();
		try {
			log.info("Checking if user has any devices connected");
			PreparedStatement pst = dataSource.getConnection()
					.prepareStatement("SELECT deviceID FROM UserConnectedDevice where userId = ?");
			pst.setInt(1, userID);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				device.setDeviceID(rs.getInt("deviceID"));
				log.info("User has device connected {} ", device.getDeviceID());
			}
		} catch (SQLException e) {
			log.error("SqlException in checkIfUserHasDevice {}", e);
		}
		return device;
	}
}
