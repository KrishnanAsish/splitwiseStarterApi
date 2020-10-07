package com.splitwise.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.splitwise.pojo.Expense;
import com.splitwise.pojo.ExpenseParticipants;
import com.splitwise.vo.ExpenseDetailVO;
import com.splitwise.vo.ExpenseParticipantDetailVO;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Component
public class VoToEntityMapperUtil {

	MapperFactory mapperFactory;
	
	@PostConstruct
	public void init() {
		mapperFactory = new DefaultMapperFactory.Builder().build();

	}
	
	public Expense getExpenseFromExpenseDetailVO(ExpenseDetailVO expenseDetailVo) {
		mapperFactory.classMap(ExpenseDetailVO.class, Expense.class).byDefault();
		mapperFactory.classMap(ExpenseDetailVO.class, Expense.class)
	      .field("teamExpenseAmount", "amount").field("description", "description")
	      .register();
	    MapperFacade mapper = mapperFactory.getMapperFacade();
	    Expense expense = mapper.map(expenseDetailVo, Expense.class);
	    return expense;
	}
	
	public ExpenseParticipants getExpenseParticipantFromExpenseParticipantDetailVO(ExpenseParticipantDetailVO expenseParticipantDetailVO) {
		ExpenseParticipants expenseParticipant = new ExpenseParticipants();
		BeanUtils.copyProperties(expenseParticipantDetailVO, expenseParticipant);
		return expenseParticipant;
	}
}
