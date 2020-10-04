package com.splitwise.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.splitwise.pojo.Aggregates;

@Repository
public interface AggregatesDao extends CrudRepository<Aggregates, Integer> {

	public List<Aggregates> findByFromId(int fromId);
}
