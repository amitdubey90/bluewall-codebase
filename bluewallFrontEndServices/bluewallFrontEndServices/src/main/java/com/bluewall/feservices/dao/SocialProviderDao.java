package com.bluewall.feservices.dao;

import com.bluewall.feservices.bean.SocialProvider;

public interface SocialProviderDao {

	public SocialProvider getProviderIdByName(String providerName);
}
