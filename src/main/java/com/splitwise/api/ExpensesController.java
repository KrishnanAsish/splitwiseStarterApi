package com.splitwise.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.splitwise.service.ExpensesService;
import com.splitwise.service.UserService;
import com.splitwise.vo.ExpenseDetailVO;

@RestController
public class ExpensesController {

	@Autowired
	private ExpensesService expensesService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method=RequestMethod.POST,value="/expenses")
	public String recordExpense(@Valid @RequestBody ExpenseDetailVO expenseDetail) {
		expensesService.saveExpenseRequest(expenseDetail);
		return "OK";
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	 
	    ex.getBindingResult().getAllErrors().forEach(error -> 
	        errors.put("Error details:", error.getDefaultMessage()));
	     
	    return errors;
	}
	
}
