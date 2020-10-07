package com.splitwise.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Aggregates {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int fromId;
	
	private int toId;
	
	private double balance;

	public Aggregates() {
		
	}
	
	public Aggregates(int fromId,int toId, double balance) {
		this.fromId=fromId;
		this.toId=toId;
		this.balance=balance;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
