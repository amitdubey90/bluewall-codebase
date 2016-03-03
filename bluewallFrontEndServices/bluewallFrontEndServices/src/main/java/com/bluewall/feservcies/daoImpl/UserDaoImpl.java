package com.bluewall.feservcies.daoImpl;

import org.springframework.stereotype.Repository;

import com.bluewall.feservices.dao.UserDao;
import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.bean.UserProfile;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Override
	public UserCredential getUserConnectionCredsById(int userId,int providerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUserCredentials(UserCredential newUserCreds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUserCredentials(UserCredential newUserCreds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createUser(UserProfile user) {
		// TODO Auto-generated method stub
		
	}

}
