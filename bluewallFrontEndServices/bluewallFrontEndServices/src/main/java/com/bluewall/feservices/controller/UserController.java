package com.bluewall.feservices.controller;

import com.bluewall.feservices.bean.UserPrincipal;
import com.bluewall.feservices.dao.UserDao;
import com.bluewall.util.bean.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
public class UserController {

	@Autowired
	UserDao userDao;

	@RequestMapping("/user")
	public UserPrincipal user(Principal user, HttpSession session) {

		// Hack to put userPrincipal in session
		UserPrincipal userPrincipal = userDao.loadUserByName(user.getName());
		if (userPrincipal != null) {
			session.setAttribute("userPrincipal", userPrincipal);
			return userPrincipal;
		}
		return null;
	}
	
}
