package com.bluewall.feservices.service;

public interface ConnectionService {

	boolean authroizeApp(String provider, int userId);

	void connectUser(String code,int userId);

}
