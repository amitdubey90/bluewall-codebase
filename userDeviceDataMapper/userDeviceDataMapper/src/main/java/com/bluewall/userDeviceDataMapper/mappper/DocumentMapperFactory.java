package com.bluewall.userDeviceDataMapper.mappper;

import com.bluewall.userDeviceDataMapper.util.Constants;
import org.bson.Document;

/**
 * Factory that holds instances of {@link FitbitDocumentMapper} and {@link JawboneDocumentMapper}
 */
public class DocumentMapperFactory {
    private static final ActivityLogMapper<Document> FITBIT_MAPPER = new FitbitDocumentMapper();
    private static final ActivityLogMapper<Document> JAWBONE_MAPPER = new JawboneDocumentMapper();

    public static ActivityLogMapper<Document> getActivityMapper(String deviceType) {
        if (Constants.DEVICE_TYPE_FITBIT.equals(deviceType)) {
            return FITBIT_MAPPER;
        } else if (Constants.DEVICE_TYPE_JAWBONE.equals(deviceType)) {
            return JAWBONE_MAPPER;
        }
        return null;
    }
}
