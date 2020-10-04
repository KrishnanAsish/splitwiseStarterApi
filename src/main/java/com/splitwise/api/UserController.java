package com.splitwise.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.splitwise.pojo.Aggregates;
import com.splitwise.pojo.UserProfile;
import com.splitwise.service.AggregatesService;
import com.splitwise.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AggregatesService aggregService;
	
	@RequestMapping(method=RequestMethod.POST,value="/users")
	public List<UserProfile> registerNewUser(@RequestBody UserProfile user) {
		userService.addNewUser(user);
		return getAllUsers();
	}

	@RequestMapping(value="/users")
	public List<UserProfile> getAllUsers() {
		return userService.getAllUsers();
	}

	/*
	 * @RequestMapping(value="/users/fetch_data") public int
	 * getUserWithPhoneNo(@PathVariable("phone_no") String phoneNo) { return
	 * userService.getUserWithPhoneNo(phoneNo); }
	 */


	@RequestMapping(value="/users/fetch_user_data") 
	public int getUserWithPhoneNo(@RequestParam("phone_no") String phoneNo,@RequestParam("email_id") String emailId) { 
		int result = -1;
		if(phoneNo!="") 
			result = userService.getUserWithPhoneNo(phoneNo); 
		else
			result = userService.getUserWithEmailId(emailId); 
		return result;
	}
	
	@RequestMapping(value="/users/fetch_balance_data") 
	public List<Aggregates> getUserWithPhoneNo(@RequestParam("user_id") int userId) {
		List<Aggregates> aggregatesList = aggregService.getAggregatesByFromId(userId);
		return aggregatesList;
	}

}
