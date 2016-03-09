package com.bluewall.feservices.service;

import com.bluewall.util.bean.UserCredential;

public interface ConnectionService {

	boolean storeConnectionParameters(UserCredential creds);

	void fetchUserProfile(UserCredential creds);

}
