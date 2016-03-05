package com.bluewall.feservices.service;


import com.google.api.client.auth.oauth2.TokenResponse;

public interface DeviceAuthorizationSvcIfc {
    /**
     * Converts {@link TokenResponse} to {@link com.bluewall.util.bean.AccessCredentials}
     * and initiates persistence
     * @param userId
     * @param deviceId
     * @param tokenResponse
     * @return true if successful
     */
    boolean storeUserAccessCredentials(int userId, int deviceId, TokenResponse tokenResponse);
}
