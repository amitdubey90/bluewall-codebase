package com.bluewall.util.bean;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class UserConnectedDevice {

	private int connectionID;
	private int userID;
	private int deviceID;
	private String refreshToken;
	private String accessToken;
	private Date expirationTime;
	private Date deviceConnectionTime;

}
