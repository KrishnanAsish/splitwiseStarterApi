package com.splitwise.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class GroupData {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int groupId;
	
	private String groupTitle;
	
	@ManyToOne
	@JoinColumn(name="expensesId",insertable=true,updatable=false)
	private Expense expenses;
	
	@ManyToOne
	@JoinColumn(name="userProfileId",insertable=true,updatable=false)
	private UserProfile user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupTitle() {
		return groupTitle;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}

	public Expense getExpenses() {
		return expenses;
	}

	public void setExpenses(Expense expenses) {
		this.expenses = expenses;
	}

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}
}
