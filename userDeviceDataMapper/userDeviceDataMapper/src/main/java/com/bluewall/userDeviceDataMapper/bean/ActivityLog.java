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
    private int type;
    private int distance;
    private Timestamp startTime;
    private long duration;
    private int caloriesBurnt;
    private int loggedFrom;
}
