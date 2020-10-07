package com.splitwise.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitwise.model.AggregatesDao;
import com.splitwise.model.ExpensesParticipantsDao;
import com.splitwise.pojo.ExpenseParticipants;

@Service
public class ExpensesParticipantsService {

	@Autowired
	ExpensesParticipantsDao epDao;

	@Autowired
	AggregatesDao aggregDao;

	public List<ExpenseParticipants> findAllExpensesForUser(int id){
		return epDao.findByUserProfileId(id);
	}

	public List<ExpenseParticipants> findAllExpenses(){
		List<ExpenseParticipants> list = new ArrayList<>();
		epDao.findAll().forEach(list::add);
		return list;
	}

	public void saveExpensesParticipantDetails(List<ExpenseParticipants> expensesParticipants){
		epDao.saveAll(expensesParticipants);
	}

}
