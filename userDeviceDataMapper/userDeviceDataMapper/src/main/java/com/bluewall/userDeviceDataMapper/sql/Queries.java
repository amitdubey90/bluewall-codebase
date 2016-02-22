package com.bluewall.userDeviceDataMapper.sql;

public class Queries {
    public static String ACTIVITY_LOG_INSERT = "INSERT INTO userDatabase.ActivityLog (TYPE, DISTANCE, STARTTIME, DURATION, " +
            " CALORIESBURNT, LOGGEDFROM) VALUES (?, ?, ? ,? ,? ,?)";
}
