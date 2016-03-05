package com.bluewall.feservices.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.bean.UserProfile;


@Configuration
@Component
@Service
@Repository
public interface UserDao {

	public UserCredential getUserConnectionCredsById(int userId, int providerId);

	public void updateUserCredentials(UserCredential newUserCreds);

	public void insertUserCredentials(UserCredential newUserCreds);

	public void createUser(UserProfile user);
}
