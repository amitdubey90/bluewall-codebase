package com.bluewall.userDeviceDataMapper.mappper;

import com.bluewall.userDeviceDataMapper.bean.ActivityLog;
import com.bluewall.userDeviceDataMapper.util.Constants;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;

import org.bson.Document;

/**
 * {@link Document} to {@link com.bluewall.userDeviceDataMapper.bean.ActivityLog} mapper implementation
 * for fitbit API response document.
 */
@Slf4j
public class FitbitDocumentMapper implements ActivityLogMapper<Document> {

    @Override
    public ActivityLog map(Document doc) {
        ActivityLog output = null;

        try {
            output = ActivityLog.builder()
                    .userID(getUserId(doc))
                    .loggedFrom(Constants.FITBIT_LOGGED_FROM)
                    .activityName(Constants.DEFAULT_ACTIVITY_NAME)
                    .caloriesBurnt(getCaloriesBurnt(doc)).activityLogDate(getActivityLogDate(doc))
                    .build();
        } catch (Throwable t) {
            log.debug("Could not map {} to ActivityLog.\n{}", doc.toJson(), t);
        }
        return output;
    }

    private Date getActivityLogDate(Document doc) {
		return (Date) doc.getDate(Constants.ACTIVITY_LOG_DATE_KEY);
	}

	private int getUserId(Document doc) {
        return doc.getInteger(Constants.FITBIT_USERID_KEY);
    }

    private int getCaloriesBurnt(Document doc) {
        return doc.get("summary", Document.class)
                .getInteger(Constants.FITBIT_CALORIES_BURNT_KEY);
    }
}
