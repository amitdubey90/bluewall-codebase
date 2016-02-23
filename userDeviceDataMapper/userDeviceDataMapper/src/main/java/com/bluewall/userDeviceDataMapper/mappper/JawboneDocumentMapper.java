package com.bluewall.userDeviceDataMapper.mappper;

import com.bluewall.userDeviceDataMapper.bean.ActivityLog;
import org.bson.Document;

/**
 * {@link Document} to {@link com.bluewall.userDeviceDataMapper.bean.ActivityLog} mapper implementation
 * for Jawbone API response document.
 */
public class JawboneDocumentMapper implements ActivityLogMapper<Document> {

    @Override
    public ActivityLog map(Document req) {
        //TODO implement mapping
        return null;
    }
}
