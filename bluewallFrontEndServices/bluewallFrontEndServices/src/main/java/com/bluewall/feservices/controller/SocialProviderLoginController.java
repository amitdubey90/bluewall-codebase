package com.bluewall.feservices.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bluewall.feservices.service.ConnectionService;
import com.bluewall.util.bean.UserCredential;
import com.bluewall.util.client.SocialConnectionProvidersInterface;
import com.bluewall.util.common.SocialConnectionProviders;
import com.bluewall.util.factory.SocialConnectionFactory;

@RestController
@RequestMapping("/social")
public class SocialProviderLoginController {

	@Autowired
	ConnectionService connService;

	@RequestMapping(value = "/register/{provider}", method = RequestMethod.GET)
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
	@ResponseBody
	public void accessCode(@RequestParam("code") String code, @PathVariable String provider) throws Exception {
		try {

			if (provider.equals(SocialConnectionProviders.FACEBOOK.getName())) {
				SocialConnectionProvidersInterface facebook = SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.FACEBOOK);
				UserCredential creds = facebook.getNewAccessToken(code);
				connService.storeConnectionParameters(creds);

			} else if (provider.equals(SocialConnectionProviders.GOOGLE.getName())) {
				SocialConnectionFactory.getSocialProviderInstance()
						.getConnectionInstance(SocialConnectionProviders.FACEBOOK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// return "view";
	}

}
