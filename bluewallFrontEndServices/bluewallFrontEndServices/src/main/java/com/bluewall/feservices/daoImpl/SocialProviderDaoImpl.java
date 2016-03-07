package com.bluewall.feservices.daoImpl;

import org.springframework.stereotype.Repository;

import com.bluewall.feservices.bean.SocialProvider;
import com.bluewall.feservices.dao.SocialProviderDao;

@Repository("socialDao")
public class SocialProviderDaoImpl implements SocialProviderDao {

	@Override
	public SocialProvider getProviderIdByName(String providerName) {
		
		return null;
	}

}
