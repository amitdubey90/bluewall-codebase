package com.bluewall.feservices.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.bluewall.util.common.SocialConnectionProviders;
import com.bluewall.util.factory.SocialConnectionFactory;

@Controller
@RequestMapping("/register")
public class SocialProviderLoginController {

	@Autowired
	ConnectionService connService;

	@RequestMapping(value = "/social")

	public String getSocialLoginView() {

		return "socialLogin";
	}

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
				socialProvider = SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.GOOGLE);
				authUrl = socialProvider.AuthorizationRequestUrl();

			}
			response.sendRedirect(authUrl);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/callback/{provider}", params = "code", method = RequestMethod.GET)

	public ModelAndView accessCode(@RequestParam("code") String code, @PathVariable String provider, Model model)
			throws Exception {
		UserProfile userProfile = new UserProfile();
		try {

			if (provider.equals(SocialConnectionProviders.FACEBOOK.getName())) {
				SocialConnectionProvidersInterface facebook = SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.FACEBOOK);
				UserCredential creds = facebook.getNewAccessToken(code);
				userProfile = facebook.fetchUserProfile(creds);

			} else if (provider.equals(SocialConnectionProviders.GOOGLE.getName())) {
				SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.GOOGLE);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelAndView mv = new ModelAndView();
		mv.addObject("userProfileData", userProfile);
		mv.setViewName("registration");

		return mv;
	}

}
