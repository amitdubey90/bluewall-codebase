package com.bluewall.userDeviceDataMapper.sql;

/**
 * This class holds all SQL queries used in this module
 */
public class Queries {
    public static final String ACTIVITY_LOG_INSERT = "INSERT INTO userDatabase.ActivityLog " +
            "(TYPE, DISTANCE, STARTTIME, DURATION, " +
            " CALORIESBURNT, LOGGEDFROM) VALUES (?, ?, ? ,? ,? ,?)";
}
