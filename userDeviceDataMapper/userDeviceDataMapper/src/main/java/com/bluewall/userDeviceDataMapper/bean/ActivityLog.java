package com.bluewall.userDeviceDataMapper.bean;

import java.sql.Date;

import lombok.Data;

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
    private Date activityLogDate;
}
