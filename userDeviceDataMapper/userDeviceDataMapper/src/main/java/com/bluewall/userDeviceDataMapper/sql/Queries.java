package com.bluewall.userDeviceDataMapper.sql;

/**
 * This class holds all SQL queries used in this module
 */
public class Queries {
    public static final String ACTIVITY_LOG_INSERT = "INSERT INTO userDatabase.ActivityLog " +
            "(userID, name, distance, activityLogDate, duration, " +
            " caloriesBurnt, loggedFrom) VALUES (?, ?, ?, ? ,? ,? ,?)";

    public static final String MAX_LOG_ID_FROM_DEVICE = "select max(activityLogID) as maxId from userDatabase.ActivityLog " +
            "where loggedFrom = 10 or loggedFrom = 11";

}
