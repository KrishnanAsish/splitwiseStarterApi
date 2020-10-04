package com.splitwise.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.splitwise.pojo.UserProfile;

@Repository
public interface UserDao extends CrudRepository<UserProfile, Integer> {

	public UserProfile findByPhoneNo(String phoneNo);

	public UserProfile findByEmailId(String emailId);
	
}
