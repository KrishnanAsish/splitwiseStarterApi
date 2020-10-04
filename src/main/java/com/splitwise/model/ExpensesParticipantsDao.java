package com.splitwise.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.splitwise.pojo.ExpensesParticipants;


@Repository
public interface ExpensesParticipantsDao extends CrudRepository<ExpensesParticipants, Integer> {
		
	public List<ExpensesParticipants> findByUserProfileId(int id);
}
