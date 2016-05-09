package com.bluewall.feservices.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.dao.ProfileDao;
import com.bluewall.feservices.service.ProfileService;
import com.bluewall.util.bean.UserProfile;


@Service
public class ProfileServiceImpl implements ProfileService{
	
	@Autowired
	ProfileDao profileDao;

	@Override
	public UserProfile getUserProfile(int userId) {
		
		return profileDao.getUserProfile(userId);
	}

	@Override
	public void updateUserProfile(UserProfile userProfile, int userId) {
		
		profileDao.updateUserProfile(userProfile, userId);
		
	}

}
