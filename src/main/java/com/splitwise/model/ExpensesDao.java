package com.splitwise.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.splitwise.pojo.Expenses;

@Repository
public interface ExpensesDao extends CrudRepository<Expenses, Integer> {

	public Expenses findById(int id);
}
