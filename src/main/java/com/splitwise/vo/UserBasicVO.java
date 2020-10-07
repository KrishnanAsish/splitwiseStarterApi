package com.splitwise.vo;

import javax.validation.constraints.NotEmpty;


import com.splitwise.validator.UserRegisterValidator;

@UserRegisterValidator
public class UserBasicVO {

	private int userId;
	
	private String emailId;
	
	private String phoneNo;
	
	@NotEmpty
	private String name;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserBasicVO [userId=" + userId + ", emailId=" + emailId + ", phoneNo=" + phoneNo + ", name=" + name
				+ "]";
	}
	
}
