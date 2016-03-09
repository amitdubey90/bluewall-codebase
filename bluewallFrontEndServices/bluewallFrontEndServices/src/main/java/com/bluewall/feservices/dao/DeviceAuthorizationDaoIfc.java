package com.bluewall.feservices.dao;
import com.bluewall.util.bean.AccessCredentials;
import com.bluewall.util.bean.UserCredential;

public interface DeviceAuthorizationDaoIfc {
    /**
     * Stores {@link AccessCredentials} to persistence
     * @param credentials
     * @return true if successfully persisted
     */
    boolean storeUserAccessToken(AccessCredentials credentials);
}
