package com.bluewall.feservices.dao;

import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.bean.UserProfile;

public interface UserDao {

	public UserCredential getUserConnectionCredsById(int userId, int providerId);

	public void updateUserCredentials(UserCredential newUserCreds);

	public void insertUserCredentials(UserCredential newUserCreds);

	public void createUser(UserProfile user);
}
