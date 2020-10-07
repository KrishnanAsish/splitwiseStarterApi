package com.splitwise.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ExpenseParticipants {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int participId;
	
	@ManyToOne
	@JoinColumn(name="expenseId", insertable=true,updatable=false)
	private Expense expense;
	
	@ManyToOne
	@JoinColumn(name="userProfileId", insertable=true,updatable=false)
	private UserProfile userProfile;
	
	private int share;
	
	private double contribution_exact;
	
	private int contribution_percentage;

	public int getParticipId() {
		return participId;
	}

	public void setParticipId(int participId) {
		this.participId = participId;
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public double getContribution_exact() {
		return contribution_exact;
	}

	public void setContribution_exact(double contribution_exact) {
		this.contribution_exact = contribution_exact;
	}

	public int getContribution_percentage() {
		return contribution_percentage;
	}

	public void setContribution_percentage(int contribution_percentage) {
		this.contribution_percentage = contribution_percentage;
	} 
	
}
