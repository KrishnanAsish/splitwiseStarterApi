package com.splitwise.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitwise.model.AggregatesDao;
import com.splitwise.model.UserDao;
import com.splitwise.pojo.Aggregates;
import com.splitwise.utils.CommonUserUtils;
import com.splitwise.vo.UserBasicVO;

@Service
public class AggregatesService {

	@Autowired
	CommonUserUtils userUtils;
	
	@Autowired
	AggregatesDao aggregatesDao;
	
	@Autowired
	UserDao userDao;
	
	public List<Aggregates> getAggregatesByFromId(UserBasicVO userVo){
		List<Aggregates> list = new ArrayList<>();
		int userId = userUtils.getUserData(userVo);
		aggregatesDao.findByFromId(userId).forEach(list::add);
		return list;
		
	}
	
	public List<Aggregates> getAllAggregates(){
		List<Aggregates> list = new ArrayList<>();
		aggregatesDao.findAll().forEach(list::add);
		return list;
		
	}
}
