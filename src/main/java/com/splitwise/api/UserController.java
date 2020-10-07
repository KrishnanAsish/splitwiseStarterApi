package com.splitwise.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.splitwise.pojo.Aggregates;
import com.splitwise.pojo.UserProfile;
import com.splitwise.service.AggregatesService;
import com.splitwise.service.UserService;
import com.splitwise.utils.CommonUserUtils;
import com.splitwise.vo.UserBasicVO;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private CommonUserUtils userUtils;
	
	@Autowired
	private AggregatesService aggregService;
	
	@RequestMapping(method=RequestMethod.POST,value="/users/register")
	public int registerNewUser(@Valid @RequestBody UserBasicVO userVo) {
		int id = userService.addNewUser(userVo);
		return id;
	}

	@RequestMapping(value="/users")
	public List<UserProfile> getAllUsers() {
		return userService.getAllUsers();
	}


	@RequestMapping(value="/users/login") 
	public int getUserData(@RequestBody UserBasicVO userVo) {
		return userUtils.getUserData(userVo);
	}
	
	@RequestMapping(value="/users/fetch_balances") 
	public List<Aggregates> getUserWithPhoneNo(@RequestBody UserBasicVO userVo) {
		List<Aggregates> aggregatesList = aggregService.getAggregatesByFromId(userVo);
		return aggregatesList;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	 
	    ex.getBindingResult().getAllErrors().forEach(error -> 
	        errors.put("Error details:", error.getDefaultMessage()));
	     
	    return errors;
	}
}
