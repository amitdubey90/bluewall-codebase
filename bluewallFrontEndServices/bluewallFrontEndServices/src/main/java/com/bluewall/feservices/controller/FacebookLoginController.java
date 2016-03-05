package com.bluewall.feservices.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bluewall.feservcies.daoImpl.SocialProviderDaoImpl;
import com.bluewall.feservices.dao.UserDao;
import com.bluewall.feservices.service.ConnectionService;
import com.bluewall.util.client.SocialConnectionProvidersInterface;
import com.bluewall.util.common.SocialConnectionProviders;
import com.bluewall.util.factory.SocialConnectionFactory;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@RestController
@RequestMapping("/social")
public class FacebookLoginController {

	@Autowired
	ConnectionService connService;

//	@Autowired
//	UserDao userDao;
//
//	@Autowired
//	SocialProviderDaoImpl socialDao;

	private static final String FACEBOOK_SCOPE = "email,user_about_me";
	private static final String GOOGLE_SCOPE = "profile";
	private static final String REDIRECT_URI = "http://localhost:8080/social/callback";
	private static final String CLIENT_ID = "1690293301249102";
	private static final String APP_SECRET = "316eb6df004a0575ce9961815724ede9";
	private static final String FACEBOOK_DIALOG_OAUTH = "https://www.facebook.com/dialog/oauth";
	private static final String GOOGLE_DIALOG_OAUTH = "https://accounts.google.com/o/oauth2/auth";
	private static final String ACCESS_TOKEN = "https://graph.facebook.com/oauth/access_token";

	@RequestMapping(value = "/register/{provider}", method = RequestMethod.GET)
	public void register(HttpServletRequest request, HttpServletResponse response, @PathVariable String provider)
			throws Exception {
		try {

			if (provider.equals(SocialConnectionProviders.FACEBOOK)) {
				String authUrl = getRequestUrlForFacebook();
				response.sendRedirect(authUrl);
			} else if (provider.equals(SocialConnectionProviders.GOOGLE)) {
				String authUrl = getRequestUrlForGoogle();
				response.sendRedirect(authUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	private String getRequestUrlForFacebook() {

		return new AuthorizationRequestUrl(FACEBOOK_DIALOG_OAUTH, CLIENT_ID, Arrays.asList("code"))
				.set("scope", FACEBOOK_SCOPE).setRedirectUri(REDIRECT_URI).build();

	}

	private String getRequestUrlForGoogle() {

		return new AuthorizationRequestUrl(GOOGLE_DIALOG_OAUTH, CLIENT_ID, Arrays.asList("code"))
				.set("scope", GOOGLE_SCOPE).setRedirectUri(REDIRECT_URI).build();

	}

	@RequestMapping(value = "/callback", params = "error_reason", method = RequestMethod.GET)
	@ResponseBody
	public void error(@RequestParam("error_reason") String errorReason, @RequestParam("error") String error,
			@RequestParam("error_description") String description, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, description);
			System.out.println(errorReason);
			System.out.println(error);
			System.out.println(description);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/callback", params = "code", method = RequestMethod.GET)
	@ResponseBody
	public void accessCode(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

			// TokenResponse resp = getAccessToken(code);
			// System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return "view";
	}

	public void getAccessToken(String code) throws IOException {

		// AuthorizationCodeTokenRequest authCodeReq = new
		// AuthorizationCodeTokenRequest(new NetHttpTransport(),
		// new JacksonFactory(), new GenericUrl(ACCESS_TOKEN),
		// code).setRedirectUri(REDIRECT_URI)
		// .setClientAuthentication(new BasicAuthentication(CLIENT_ID,
		// APP_SECRET));

		// return authCodeReq.execute();
	}
}
