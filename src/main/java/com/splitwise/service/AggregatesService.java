package com.splitwise.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitwise.model.AggregatesDao;
import com.splitwise.pojo.Aggregates;

@Service
public class AggregatesService {

	@Autowired
	AggregatesDao aggregatesDao;
	
	public List<Aggregates> getAggregatesByFromId(int fromId){
		List<Aggregates> list = new ArrayList<>();
		aggregatesDao.findByFromId(fromId).forEach(list::add);
		return list;
		
	}
	
	public List<Aggregates> getAllAggregates(){
		List<Aggregates> list = new ArrayList<>();
		aggregatesDao.findAll().forEach(list::add);
		return list;
		
	}
}
