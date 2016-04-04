package com.bluewall.feservices.dao;

import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.util.bean.UserProfile;

public interface UserDao {

	void createUser(UserProfile user);

	UserPrincipal loadUserByName(String emailID);
}
