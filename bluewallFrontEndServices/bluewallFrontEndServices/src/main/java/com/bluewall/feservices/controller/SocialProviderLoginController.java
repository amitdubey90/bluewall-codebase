package com.bluewall.feservices.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.bean.UserProfile;
import com.bluewall.util.client.SocialConnectionProvidersInterface;
import com.bluewall.util.common.SocialConnectionProviders;
import com.bluewall.util.factory.SocialConnectionFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * This class handles requests for social connection providers like facebook and
 * google. The application is authorized by the user. Once the application is
 * authorized, an api call will be made to fetch user profile information like
 * first name, last name, email, etc
 * 
 * @author rainashastri
 *
 */
@Slf4j
@Controller
@RequestMapping("/register")
public class SocialProviderLoginController {

	/**
	 * This method fetches authorization request url based on the provider in
	 * the request. The response of this method is a redirect to the provider's
	 * login page
	 * 
	 * @param response
	 *            Auth URL
	 * @param provider
	 *            Google / Facebook
	 * @throws Exception
	 */
	@RequestMapping(value = "/social/{provider}", method = RequestMethod.GET)

	public void register(HttpServletResponse response, @PathVariable String provider) throws Exception {
		SocialConnectionProvidersInterface socialProvider = null;
		String authUrl = null;
		try {
			if (provider.equals(SocialConnectionProviders.FACEBOOK.getName())) {
				log.info("Fetching Facebook instance");
				socialProvider = SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.FACEBOOK);

			} else if (provider.equals(SocialConnectionProviders.GOOGLE.getName())) {
				log.info("Fetching Google instance");
				socialProvider = SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.GOOGLE);
			}

			if (null != socialProvider) {
				log.debug("Social connection Instance Created : {}", provider);
				authUrl = socialProvider.AuthorizationRequestUrl();
				if (authUrl != null) {
					log.info(
							"Authroization request url fetched successfully. Now Redirecting to Social connection provider website");
					response.sendRedirect(authUrl);
				} else {
					log.debug("Error while authorizing user", authUrl);
					response.sendRedirect("registration");
				}
			} else {
				log.debug("Could not create instance for social connection provider: {}", provider);
			}

		} catch (Exception e) {
			log.error("Exception occured while authorizing user", e);
		}
	}

	/**
	 * This method contains the authorization code returned by the provider.
	 * With this token the method makes an api call to fetch the access token
	 * for the user. The access token fetched will be used to get basic profile
	 * info of the user from social website.
	 * 
	 * @param code
	 *            Intermediate code generated while fetching access token
	 * @param provider
	 *            Google / Facebook
	 * @throws Exception
	 */

	@RequestMapping(value = "/callback/{provider}", params = "code", method = RequestMethod.GET)

	public ModelAndView accessCode(@RequestParam("code") String code, @PathVariable String provider) throws Exception {
		UserProfile userProfile = new UserProfile();
		SocialConnectionProvidersInterface socialProvider = null;
		try {
			log.info("Callback from {}", provider);
			if (provider.equals(SocialConnectionProviders.FACEBOOK.getName())) {
				socialProvider = SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.FACEBOOK);
			} else {
				socialProvider = SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.GOOGLE);
			}
			if (null != socialProvider) {
				UserCredential creds = socialProvider.getNewAccessToken(code);
				if (null != creds) {
					log.error("User Credentials Fetched");
					userProfile = socialProvider.fetchUserProfile(creds);
				} else
					log.error("Exception while getting access token", creds);
			}

		} catch (Exception e) {
			log.error("Exception while getting access token");
		}

		ModelAndView mv = new ModelAndView();
		mv.addObject("userProfileData", userProfile);
		mv.setViewName("registration");

		return mv;
	}

}
