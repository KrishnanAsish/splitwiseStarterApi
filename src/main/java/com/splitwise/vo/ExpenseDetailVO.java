package com.splitwise.vo;

import com.splitwise.utils.SplitWiseConstants;
import com.splitwise.validator.ExpenseValidator;

import java.util.List;

@ExpenseValidator
public class ExpenseDetailVO {

	private SplitWiseConstants.ExpenseType expenseType;
	
	private double teamExpenseAmount;
	
	private String description;
	
	private List<ExpenseParticipantDetailVO> participantDetails;

	public SplitWiseConstants.ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(SplitWiseConstants.ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public double getTeamExpenseAmount() {
		return teamExpenseAmount;
	}

	public void setTeamExpenseAmount(double teamExpenseAmount) {
		this.teamExpenseAmount = teamExpenseAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ExpenseParticipantDetailVO> getParticipantDetails() {
		return participantDetails;
	}

	public void setParticipantDetails(List<ExpenseParticipantDetailVO> participantDetails) {
		this.participantDetails = participantDetails;
	}
	
}
