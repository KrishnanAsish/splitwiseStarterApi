package com.splitwise.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitwise.model.UserDao;
import com.splitwise.pojo.UserProfile;
import com.splitwise.vo.UserBasicVO;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	public int addNewUser(UserBasicVO userVo) {
		UserProfile userEntity = new UserProfile();
		BeanUtils.copyProperties(userVo, userEntity);
		userDao.save(userEntity);
		return userEntity.getId();
	}

	public List<UserProfile> getAllUsers() {
		List<UserProfile> list = new ArrayList<>();
		userDao.findAll().forEach(list::add);
		return list;
	}

}
