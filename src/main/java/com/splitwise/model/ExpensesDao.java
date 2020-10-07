package com.splitwise.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.splitwise.pojo.Expense;

@Repository
public interface ExpensesDao extends CrudRepository<Expense, Integer> {

	public Expense findById(int id);
}
