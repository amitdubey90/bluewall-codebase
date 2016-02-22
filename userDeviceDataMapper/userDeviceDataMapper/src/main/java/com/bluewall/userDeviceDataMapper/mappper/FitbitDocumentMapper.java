package com.bluewall.userDeviceDataMapper.mappper;

import com.bluewall.userDeviceDataMapper.bean.ActivityLog;
import com.bluewall.userDeviceDataMapper.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

/**
 *
 */
@Slf4j
public class FitbitDocumentMapper implements ActivityLogMapper<Document> {

    @Override
    public ActivityLog map(Document doc) {
        ActivityLog output = null;

        try {
            //TODO add userId and logID
            output = ActivityLog.builder()
                    .loggedFrom(Constants.FITBIT_LOGGED_FROM)
                    .type(Constants.DEFAULT_MAPPER_TYPE)
                    .caloriesBurnt(getCaloriesBurnt(doc))
                    .build();
        } catch (Throwable t) {
            log.debug("Could not map {} to ActivityLog.\\n{}", doc.toJson(), t);
        }
        return output;
    }

    private int getCaloriesBurnt(Document doc) {
        return doc.get("summary", Document.class)
                .getInteger(Constants.FITBIT_CALORIES_BURNT_KEY);
    }
}
