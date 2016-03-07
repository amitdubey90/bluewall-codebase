package com.bluewall.util.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class UserProfile {
		
		private String firstName;
		private String lastName;
		private String emailID;
		private String passwd;
		private String contactNumber;
		
		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public String getActivityLevel() {
			return activityLevel;
		}

		public void setActivityLevel(String activityLevel) {
			this.activityLevel = activityLevel;
		}

		public String getCurrentLocation() {
			return currentLocation;
		}

		public void setCurrentLocation(String currentLocation) {
			this.currentLocation = currentLocation;
		}

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
		

		public String getGoalType() {
			return goalType;
		}

		public void setGoalType(String goalType) {
			this.goalType = goalType;
		}

		public int getTargetWeight() {
			return targetWeight;
		}

		public void setTargetWeight(int targetWeight) {
			this.targetWeight = targetWeight;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getEmailID() {
			return emailID;
		}

		public void setEmailID(String emailID) {
			this.emailID = emailID;
		}

		public String getPasswd() {
			return passwd;
		}

		public void setPasswd(String passwd) {
			this.passwd = passwd;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getContactNumber() {
			return contactNumber;
		}

		public void setContactNumber(String contactNumber) {
			this.contactNumber = contactNumber;
		}

}
