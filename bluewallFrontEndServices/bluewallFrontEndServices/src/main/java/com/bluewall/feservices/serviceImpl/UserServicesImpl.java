package com.bluewall.feservices.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.dao.UserDao;
import com.bluewall.feservices.service.UserServices;
import com.bluewall.util.bean.UserProfile;

@Service
public class UserServicesImpl implements UserServices{
	
	@Autowired
	UserDao userDao;
	
	@Override
	public void createUser(UserProfile userRegister) {
		userDao.createUser(userRegister);
	}
	

}
