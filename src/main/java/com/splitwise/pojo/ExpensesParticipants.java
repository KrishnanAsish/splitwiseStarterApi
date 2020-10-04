package com.splitwise.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ExpensesParticipants {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int participId;
	
	@ManyToOne
	@JoinColumn(name="expenses_id", insertable=true,updatable=false)
	private Expenses expense;
	
	@ManyToOne
	@JoinColumn(name="userprofile_id", insertable=true,updatable=false)
	private UserProfile userProfile;
	
	private float share;
	
	private boolean isShareInPercentile;
	
	private int contribution;

	public Expenses getExpense() {
		return expense;
	}

	public void setExpense(Expenses expense) {
		this.expense = expense;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public float getShare() {
		return share;
	}

	public void setShare(float share) {
		this.share = share;
	}

	public boolean isShareInPercentile() {
		return isShareInPercentile;
	}

	public void setShareInPercentile(boolean isShareInPercentile) {
		this.isShareInPercentile = isShareInPercentile;
	}

	public int getContribution() {
		return contribution;
	}

	public void setContribution(int contribution) {
		this.contribution = contribution;
	}
	
}
