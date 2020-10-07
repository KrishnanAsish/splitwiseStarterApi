package com.splitwise.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitwise.model.UserDao;
import com.splitwise.pojo.UserProfile;
import com.splitwise.vo.UserBasicVO;

@Service
public class CommonUserUtils {

	@Autowired
	UserDao userDao;

	public int getUserWithPhoneNo(String phoneNo) {
		UserProfile userDetails = userDao.findByPhoneNo(phoneNo);
		if(userDetails!=null) return userDetails.getId();
		else return -1;
	}

	public int getUserWithEmailId(String emailId) {
		UserProfile userDetails = userDao.findByEmailId(emailId);
		if(userDetails!=null) return userDetails.getId();
		else return -1;
	}

	public int getUserData(UserBasicVO userVo) {
		int result = -1;
		if(userVo.getEmailId()!=null && userVo.getEmailId().trim().length()>0)
			result = getUserWithEmailId(userVo.getEmailId());
		else if(userVo.getPhoneNo()!=null && userVo.getPhoneNo().trim().length()>0)
			result = getUserWithPhoneNo(userVo.getPhoneNo());
		System.out.println("Searched for user data:"+userVo);
		return result;
	}
}
