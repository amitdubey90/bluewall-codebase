package com.bluewall.feservices.bean;

public class UserInfo {
	private String fName;
	private String lName;
	private String emailID;
	private String passwd;
	private int contactInfo;

	public int getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(int contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
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

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}
	
}
