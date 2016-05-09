package com.bluewall.feservices.service;

import com.bluewall.util.bean.UserProfile;

public interface ProfileService {

	public UserProfile getUserProfile(int userId);
	
	public void updateUserProfile(UserProfile userProfile, int userId);
	
}
