package com.bluewall.util.bean;

import lombok.Data;
import lombok.ToString;

@Data

@ToString
public class UserProfile {

	
	private String firstName;
	private String lastName;
	private String emailID;
	private String password;
	private String contactNumber;
	private int age;
	private String gender;
	private int height;
	private int weight;
	private String activityLevel;
	private String currentLocation;
	private String goalType;
	private int targetWeight;
	private String startDate;
	private String endDate;
	
	public UserProfile() {
		// TODO Auto-generated constructor stub
	}
	

}
