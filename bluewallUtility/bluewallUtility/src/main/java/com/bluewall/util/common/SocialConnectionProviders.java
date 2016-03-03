package com.bluewall.util.common;

public enum SocialConnectionProviders {
	FACEBOOK("facebook"), GOOGLE("google");

	private String provider;

	SocialConnectionProviders(String provider) {
		this.provider = provider;
	}

	public String getName() {
		return this.provider;
	}
}
