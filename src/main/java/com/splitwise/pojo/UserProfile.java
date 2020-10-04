package com.splitwise.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserProfile {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String emailId;

	private String phoneNo;

	private String name;

	public UserProfile() {

	}

	public UserProfile(String emailId,String phoneNo,String name) {
		this.emailId=emailId; 
		this.phoneNo=phoneNo; 
		this.name=name; 
		this.id = emailId.hashCode() + phoneNo.hashCode(); 
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
