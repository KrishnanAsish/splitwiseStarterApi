package com.splitwise.utils;

public interface SplitWiseConstants {
	
	enum ExpenseType {
		SETTLEMENT_TO_INDIVIDUAL("S"),
		EXPENSE_IN_GROUP("E");

		private String expenseType;
		
		ExpenseType(String string) {
			this.expenseType=string;
		}
		
		public String getExpenseType() {
			return expenseType;
		}
	}
}
