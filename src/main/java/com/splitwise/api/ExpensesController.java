package com.splitwise.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.splitwise.pojo.Expenses;
import com.splitwise.pojo.ExpensesParticipants;
import com.splitwise.pojo.UserProfile;
import com.splitwise.service.ExpensesParticipantsService;
import com.splitwise.service.ExpensesService;
import com.splitwise.service.UserService;

@RestController
public class ExpensesController {

	@Autowired
	private ExpensesService expensesService;
	
	@Autowired
	private ExpensesParticipantsService expensesParticipantsService;

	@Autowired
	UserService userService;
	
	@RequestMapping(value="/expenses/{id}")
	public List<ExpensesParticipants> viewAllUserExpenses(@PathVariable int id) {
		return expensesParticipantsService.findAllExpensesForUser(id);
	}

	@RequestMapping(value="/expenses/view_all")
	public List<Expenses> viewAllExpenses() {
		return expensesService.findAllExpenses();
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/expenses")
	public String recordExpense(@RequestBody Expenses expenses) {
		expensesService.saveExpenseRequest(expenses);
		return "OK";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/expenses/participants")
	public String recordExpenseParticpants(@RequestBody List<ExpensesParticipants> expensesParticipants,
			@RequestParam("expenses_id") Integer expensesId, @RequestParam("user_id") String userId ) {
		Expenses expenseData = expensesService.getExpenseById(expensesId);
		String[] userIdArray = userId.split(",");
		int count=0;
		for(ExpensesParticipants participants: expensesParticipants) {
			participants.setExpense(expenseData);
			UserProfile userData = userService.getUserDetailsById(Integer.valueOf(userIdArray[count++]));
			participants.setUserProfile(userData);
			
		}
		expensesParticipantsService.saveExpensesParticipantDetails(expensesParticipants);
		expensesParticipantsService.genrateAndPersistAggregateData(expenseData,expensesParticipants);
		return "OK";
	}
	
	@RequestMapping(value="/expenses/participants/view_all")
	public List<ExpensesParticipants> viewAllExpensesParticipantDetails() {
		return expensesParticipantsService.findAllExpenses();
	}

}
