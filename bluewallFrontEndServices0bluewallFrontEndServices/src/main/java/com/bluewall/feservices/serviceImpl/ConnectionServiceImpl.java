package com.bluewall.feservices.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewall.feservcies.daoImpl.SocialProviderDaoImpl;
import com.bluewall.feservices.bean.SocialProvider;
import com.bluewall.feservices.dao.UserDao;
import com.bluewall.feservices.service.ConnectionService;
import com.bluewall.feservices.util.Constants;
import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.bean.UserProfile;
import com.bluewall.util.client.SocialConnectionProvidersInterface;
import com.bluewall.util.common.SocialConnectionProviders;
import com.bluewall.util.factory.SocialConnectionFactory;

@Service
public class ConnectionServiceImpl implements ConnectionService {

	@Autowired
	SocialConnectionFactory socialFactory;

	@Autowired
	SocialConnectionProvidersInterface providerInstance;

	@Autowired
	UserDao userDao;

	@Autowired
	SocialProviderDaoImpl socialDao;

	@Override
	public boolean authroizeApp(String provider, int userId) {
		if (provider.equals(SocialConnectionProviders.FACEBOOK)) {

			

			// UserCredential credentials =
			// userDao.getUserConnectionCredsById(userId,
			// socialProvider.getProviderId());

			providerInstance = socialFactory.getConnectionInstance(SocialConnectionProviders.FACEBOOK);
			// if (null == credentials) {
			providerInstance.authorizeApp(Constants.REDIRECT_URI, Constants.FACEBOOK_SCOPES);
			// } else {
//			Date currentDate = new Date();
//			long diff = currentDate.getTime() - credentials.getCreationTime().getTime();
//			if ((diff / (60 * 60 * 1000)) > 2) {
//				UserCredential newUserCreds = providerInstance.getNewAccessToken(credentials.getAccessToken(),
//						credentials.getRefreshToken());
//				userDao.updateUserCredentials(newUserCreds);
//				// }
//			}

		}

		else if (provider.equals(SocialConnectionProviders.GOOGLE)) {
			providerInstance = socialFactory.getConnectionInstance(SocialConnectionProviders.GOOGLE);
			// check if connection already exists

		}

		return true;
	}

	@Override
	public void connectUser(String code, int userId) {

		try {
			providerInstance = socialFactory.getConnectionInstance(SocialConnectionProviders.FACEBOOK);
			UserCredential newUserCreds = providerInstance.getAccessAndRefreshToken(code);
			UserProfile profile = providerInstance.fetchUserProfile(newUserCreds);
			userDao.createUser(profile);
			userDao.insertUserCredentials(newUserCreds);
		} catch (Exception e) {

		}

	}

}
