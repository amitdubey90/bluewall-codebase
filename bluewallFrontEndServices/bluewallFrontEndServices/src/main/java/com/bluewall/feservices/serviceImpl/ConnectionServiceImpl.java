package com.bluewall.feservices.serviceImpl;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.service.ConnectionService;
import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.factory.SocialConnectionFactory;

@ComponentScan({ "com.bluewall.feservices.daoImpl" })
@Service
public class ConnectionServiceImpl implements ConnectionService {

	SocialConnectionFactory socialFactory;

//	@Autowired
//	UserDao userDao;
	
	@Override
	public boolean storeConnectionParameters(UserCredential creds) {
		
		return false;
		
		
	}

	@Override
	public void fetchUserProfile(UserCredential creds) {
		// TODO Auto-generated method stub
		
	}

	

	

}
