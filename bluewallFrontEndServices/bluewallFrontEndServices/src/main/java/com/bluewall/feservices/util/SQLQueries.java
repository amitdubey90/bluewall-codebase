package com.bluewall.feservices.util;

public class SQLQueries {
		
    public static String INSERT_USER_ACCESS_TOKEN = "INSERT INTO UserConnectedDevice (userID, deviceId, accessToken, refreshToken, expirationTime,deviceConnectionTime) values (?, ?, ?, ?, ?,?)";
    public static String GET_USER_DEVICE_CREDENTIALS = "SELECT * FROM UserConnectedDevice where userId = ?";
}
