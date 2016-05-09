package com.bluewall.feservices.dao;

import com.bluewall.util.bean.UserProfile;

public interface ProfileDao {
	
	public UserProfile getUserProfile(int userID);
	
	public void updateUserProfile(UserProfile userProfile, int userId);

}
