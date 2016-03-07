package com.bluewall.feservices.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bluewall.feservices.dao.SqlDBConnections;

public class SQLQueries {
		
    public static String INSERT_USER_ACCESS_TOKEN = "INSERT INTO UserConnectedDevice (userID, deviceId, accessToken, refreshToken, creationTime) values (?, ?, ?, ?, ?)";

}
