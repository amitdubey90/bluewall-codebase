package com.bluewall.feservices.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.bluewall.util.bean.UserCredential;

@Repository("activityDao")
public interface UserActivityDao {

	UserCredential getUserDeviceInfo(String userId) throws SQLException;

}
