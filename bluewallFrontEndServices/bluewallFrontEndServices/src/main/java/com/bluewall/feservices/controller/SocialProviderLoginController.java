package com.bluewall.feservices.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bluewall.feservices.service.ConnectionService;
import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.bean.UserProfile;
import com.bluewall.util.client.SocialConnectionProvidersInterface;
import com.bluewall.util.common.Constants;
import com.bluewall.util.common.SocialConnectionProviders;
import com.bluewall.util.factory.SocialConnectionFactory;

@Slf4j
@Controller
@RequestMapping("/register")
public class SocialProviderLoginController {

	@Autowired
	ConnectionService connService;

	@RequestMapping(value = "/social")
    public String getSocialLoginView() {
 	   return "socialLogin";
    }
	
	/**
	 * @param request Registration Request
	 * @param response Auth URL
	 * @param provider Google / Facebook
	 * @throws Exception
	 */
	@RequestMapping(value = "/social/{provider}", method = RequestMethod.GET)
	public void register(HttpServletRequest request, HttpServletResponse response, @PathVariable String provider)
			throws Exception {
		SocialConnectionProvidersInterface socialProvider = null;
		String authUrl = null;
		try {
			if (provider.equals(SocialConnectionProviders.FACEBOOK.getName())) {
				socialProvider = SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.FACEBOOK);
				authUrl = socialProvider.AuthorizationRequestUrl();

			} else if (provider.equals(SocialConnectionProviders.GOOGLE.getName())) {
				log.info("Fetching Google instance");
				socialProvider = SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.GOOGLE);
				if(socialProvider != null){
					log.debug("Google Instance Created", socialProvider);
					authUrl = socialProvider.AuthorizationRequestUrl();
					if(authUrl != null)
						response.sendRedirect(authUrl);
					else
						log.debug("Error ehile authorizing user" , authUrl);
				}
				else{
					log.debug("Exception while creating Google Instance" ,socialProvider);
				}
			}
		} catch (Exception e) {
			log.error("Exception occured while authorizing user" , e);
		}
	}

	/**
	 * @param code Intermediate code generated while fetching access token
	 * @param provider Google / Facebook
	 * @throws Exception
	 */
	@RequestMapping(value = "/callback/{provider}", params = "code", method = RequestMethod.GET)
	public String accessCode(@RequestParam("code") String code, @PathVariable String provider, Model model) throws Exception {
		UserProfile userProfile = null;
		try {
			if (provider.equals(SocialConnectionProviders.FACEBOOK.getName())) {
				SocialConnectionProvidersInterface facebook = SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.FACEBOOK);
				UserCredential creds = facebook.getNewAccessToken(code);
				userProfile = facebook.fetchUserProfile(creds);

			} else if (provider.equals(SocialConnectionProviders.GOOGLE.getName())) {
				UserCredential creds = null;
				
				log.info("Fetching Google instance");
				SocialConnectionProvidersInterface google = SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.GOOGLE);
				if(google != null){
					log.debug("Google Instance Created", google);
					creds = google.getNewAccessToken(code);
					if(creds != null){
						if(connService.storeConnectionParameters(creds))
							log.debug("Google User Credentials Saved Successfully");
						else
							log.debug("Error while saving google User Credentials");
						userProfile = google.fetchUserProfile(creds);
					}					
					else
						log.error("Exception while getting access token", creds);
				}
				else{
					log.debug("Exception while creating Google Instance" ,google);
				}  
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("userProfileData", userProfile) ;

		return "registration";
	}

}
