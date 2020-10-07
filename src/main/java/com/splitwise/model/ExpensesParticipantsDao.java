package com.splitwise.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.splitwise.pojo.ExpenseParticipants;


@Repository
public interface ExpensesParticipantsDao extends CrudRepository<ExpenseParticipants, Integer> {
		
	public List<ExpenseParticipants> findByUserProfileId(int id);
}
