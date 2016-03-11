package com.bluewall.feservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.dao.DeviceAuthorizationDaoIfc;
import com.bluewall.util.bean.AccessCredentials;
import com.bluewall.util.utility.GenericUtil;
import com.google.api.client.auth.oauth2.TokenResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeviceAuthorizationSvcImpl implements DeviceAuthorizationSvcIfc {

	@Autowired
	DeviceAuthorizationDaoIfc dao;

	public boolean storeUserAccessCredentials(int userId, int deviceId, TokenResponse tokenResponse) {
		log.info("Converting token response for user {}", userId);

		AccessCredentials credentials = AccessCredentials.builder().userId(userId)
				.accessToken(tokenResponse.getAccessToken()).refreshToken(tokenResponse.getRefreshToken())
				.expirationTime(GenericUtil.calculateExpirationTime(tokenResponse.getExpiresInSeconds()))
				.deviceId(deviceId).build();

		log.info("Sending access token to DAO for user {}", userId);
		return dao.storeUserAccessToken(credentials);
	}

}
