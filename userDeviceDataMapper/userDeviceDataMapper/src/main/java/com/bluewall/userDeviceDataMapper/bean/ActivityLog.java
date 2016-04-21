package com.bluewall.userDeviceDataMapper.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * POJO representing activity logs in the database
 */
@Data
@lombok.ToString
@lombok.Builder
public class ActivityLog {

    private int userID;
    private String activityName;
    private int distance;
    private long duration;
    private int caloriesBurnt;
    private int loggedFrom;
}
