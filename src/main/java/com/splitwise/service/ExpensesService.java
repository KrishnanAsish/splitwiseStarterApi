package com.splitwise.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitwise.model.ExpensesDao;
import com.splitwise.pojo.Expenses;

@Service
public class ExpensesService {

	@Autowired
	ExpensesDao expensesDao;

	public void saveExpenseRequest(Expenses expense) {
		expensesDao.save(expense);
	}

	public Expenses getExpenseById(int id) {
		return expensesDao.findById(id);
	}
	
	public List<Expenses> findAllExpenses() {
		List<Expenses> list = new ArrayList<>();
		expensesDao.findAll().forEach(list::add);
		return list;
	}
}
