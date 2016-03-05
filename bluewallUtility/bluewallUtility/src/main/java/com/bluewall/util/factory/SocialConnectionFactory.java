package com.bluewall.util.factory;

import com.bluewall.util.client.SocialConnectionProvidersInterface;
import com.bluewall.util.clientImpl.FacebookClient;
import com.bluewall.util.clientImpl.GoogleClient;
import com.bluewall.util.common.SocialConnectionProviders;

public class SocialConnectionFactory {

	public static SocialConnectionFactory socialFactory;

	public static SocialConnectionFactory getSocialProviderInstance() {
		if (socialFactory == null) {
			socialFactory = new SocialConnectionFactory();
		}
		return socialFactory;
	}

	public SocialConnectionProvidersInterface getConnectionInstance(SocialConnectionProviders provider) {
		SocialConnectionProvidersInterface connInstance = null;
		if (provider == SocialConnectionProviders.FACEBOOK) {
			connInstance = new FacebookClient();
		} else if (provider == SocialConnectionProviders.GOOGLE) {
			connInstance = new GoogleClient();
		}
		return connInstance;
	}
}
