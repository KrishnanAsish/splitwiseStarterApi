package com.splitwise.pojo;

public class Balance {

	private int userId;
	
	private int balance;

	public Balance() {
		
	}
	
	public Balance(int userId, int balance) {
		this.userId=userId;
		this.balance=balance;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof Balance) {
			Balance balance = (Balance) object;
			return Math.abs(this.balance)==Math.abs(balance.getBalance());
		}else return false;
	}
}
