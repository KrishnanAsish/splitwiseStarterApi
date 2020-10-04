package com.splitwise.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitwise.model.UserDao;
import com.splitwise.pojo.UserProfile;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	public void addNewUser(UserProfile user) {
		userDao.save(user);
	}

	public List<UserProfile> getAllUsers() {
		List<UserProfile> list = new ArrayList<>();
		userDao.findAll().forEach(list::add);
		return list;
	}

	public int getUserWithPhoneNo(String phoneNo) {
		/*
		 * Iterable<UserProfile> users = userDao.findAll(); Iterator<UserProfile>
		 * userIterator = users.iterator(); while(userIterator.hasNext()) { UserProfile
		 * userData = userIterator.next(); if(userData.getPhoneNo().equals(phoneNo))
		 * return userData.getId(); }
		 */
		UserProfile userDetails = userDao.findByPhoneNo(phoneNo);
		if(userDetails!=null) return userDetails.getId();
		else return -1;
	}

	public int getUserWithEmailId(String emailId) {
		UserProfile userDetails = userDao.findByEmailId(emailId);
		if(userDetails!=null) return userDetails.getId();
		else return -1;
	}
	
	public UserProfile getUserDetailsById(int id) {
		return userDao.findById(id).get();
	}
}
