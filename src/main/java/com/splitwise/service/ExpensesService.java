package com.splitwise.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitwise.model.AggregatesDao;
import com.splitwise.model.ExpensesDao;
import com.splitwise.model.ExpensesParticipantsDao;
import com.splitwise.model.UserDao;
import com.splitwise.pojo.Aggregates;
import com.splitwise.pojo.Balance;
import com.splitwise.pojo.Expense;
import com.splitwise.pojo.ExpenseParticipants;
import com.splitwise.utils.SplitWiseConstants;
import com.splitwise.utils.VoToEntityMapperUtil;
import com.splitwise.vo.ExpenseDetailVO;
import com.splitwise.vo.ExpenseParticipantDetailVO;

@Service
public class ExpensesService {

	@Autowired
	ExpensesDao expensesDao;

	@Autowired
	ExpensesParticipantsDao expensesParticipantsDao;

	@Autowired
	UserDao userDao;

	@Autowired
	AggregatesDao aggregDao;

	@Autowired
	VoToEntityMapperUtil mapperUtil;

	public void saveExpenseRequest(ExpenseDetailVO expense) {
		List<ExpenseParticipants> expenseParticipList = new ArrayList<>();
		Expense expenseEntity = mapperUtil.getExpenseFromExpenseDetailVO(expense);
		expenseEntity.setExpenseType(SplitWiseConstants.ExpenseType.EXPENSE_IN_GROUP.getExpenseType());
		for(ExpenseParticipantDetailVO participVo: expense.getParticipantDetails()) {
			ExpenseParticipants participantEntity = mapperUtil.getExpenseParticipantFromExpenseParticipantDetailVO(participVo);
			participantEntity.setExpense(expenseEntity);
			// We fetch the user id during validation stage (need to look-up or use entity here?)
			participantEntity.setUserProfile(userDao.findById(participVo.getParticipantDetail().getUserId()).get());
			expenseParticipList.add(participantEntity);
		}
		List<Balance> balanceList = generateParticipantBalances(expenseEntity,expenseParticipList);
		List<Aggregates> aggregateList = generateAggregateDataForExpense(balanceList);
		expensesDao.save(expenseEntity);
		expensesParticipantsDao.saveAll(expenseParticipList);
		aggregDao.saveAll(aggregateList);
	}

	public Expense getExpenseById(int id) {
		return expensesDao.findById(id);
	}

	public List<Expense> findAllExpenses() {
		List<Expense> list = new ArrayList<>();
		expensesDao.findAll().forEach(list::add);
		return list;
	}

	//Fetch corresponding expense record
	//For each participant, calculate balance (share * total)-contribution
	public List<Balance> generateParticipantBalances(Expense expense,
			List<ExpenseParticipants> expensesParticipants){
		List<Balance> balances = new ArrayList<>();
		double groupExpenditure = expense.getAmount();
		String expenseType = expense.getExpenseType();
		int totalShares = expensesParticipants.stream().map(p->p.getShare()).collect(Collectors.summingInt(Integer::intValue));
		if(expenseType.equals(SplitWiseConstants.ExpenseType.SETTLEMENT_TO_INDIVIDUAL.getExpenseType())) {
			//TODO Set share as 2 and 1 from sender?
		}
		for(ExpenseParticipants ep:expensesParticipants) {
			double participContribution = 0,participBalance=0,participEquilibrium=0;
			if(ep.getContribution_percentage()>0)
				participContribution = (groupExpenditure * ep.getContribution_percentage())/100;
			else
				participContribution = ep.getContribution_exact();
			participEquilibrium= (ep.getShare()*groupExpenditure)/totalShares;
			participBalance=participContribution-participEquilibrium;
			if(participBalance!=0)
				balances.add(new Balance(ep.getUserProfile().getId(),participBalance));
		}
		return balances;
	}

	/*	Create array of Balance data type 
			Sort it
			Work out settling logic from min positive integer
			Create aggregator entries
	 */
	public List<Aggregates> generateAggregateDataForExpense(List<Balance> balanceList) {
		List<Aggregates> aggregateList = new ArrayList<>();
		Balance[] balanceArray = balanceList.stream().toArray(Balance[]::new);
		// Sort Balances in ascending order
		Arrays.sort(balanceArray,Comparator.comparingDouble(Balance::getBalance));
		int rightIndex=0,leftIndex=0;
		//Track the least positive balance present
		for(int i=1;i<balanceArray.length;i++) {
			if(balanceArray[i-1].getBalance()<0 && balanceArray[i].getBalance()>=0) {
				rightIndex=i;leftIndex=i-1;break;
			}
		}
		/* Start performing aggregation from a point in the array
		 * where leftIndex always point to negative balances and vice versa
		 */
		while(rightIndex<balanceArray.length && leftIndex>=0) {
			Balance leftSideBalance = balanceArray[leftIndex];
			Balance rightSideBalance = balanceArray[rightIndex];
			// If leftSide and rightSide balances are equal in magnitude, balance them out and create an aggregation record
			if(leftSideBalance.equals(rightSideBalance)) {
				aggregateList.add(new Aggregates(leftSideBalance.getUserId(),rightSideBalance.getUserId(),leftSideBalance.getBalance()));
				aggregateList.add(new Aggregates(rightSideBalance.getUserId(),leftSideBalance.getUserId(),rightSideBalance.getBalance()));
				leftSideBalance.setBalance(0);
				rightSideBalance.setBalance(0);
				leftIndex--;rightIndex++; // Move right index towards end, move left index towards start
			}
			//If leftSide negative value is greater that rightSide value, balance out the rightSide value and update leftSide with the difference
			else if(Math.abs(leftSideBalance.getBalance())>rightSideBalance.getBalance()) {
				double absDiff = Math.abs(leftSideBalance.getBalance())-rightSideBalance.getBalance();
				aggregateList.add(new Aggregates(leftSideBalance.getUserId(),rightSideBalance.getUserId(),-1*rightSideBalance.getBalance()));
				aggregateList.add(new Aggregates(rightSideBalance.getUserId(),leftSideBalance.getUserId(),rightSideBalance.getBalance()));
				leftSideBalance.setBalance(-1*absDiff); // Update with the remaining value
				rightSideBalance.setBalance(0);
				rightIndex++; // Move rightIndex alone as this amount is now settled
			}else {
				double absDiff = rightSideBalance.getBalance() - Math.abs(leftSideBalance.getBalance());
				aggregateList.add(new Aggregates(leftSideBalance.getUserId(),rightSideBalance.getUserId(),leftSideBalance.getBalance()));
				aggregateList.add(new Aggregates(rightSideBalance.getUserId(),leftSideBalance.getUserId(),-1*leftSideBalance.getBalance()));
				leftSideBalance.setBalance(0);
				rightSideBalance.setBalance(absDiff);
				leftIndex--;
			}
		}
		return aggregateList;
	}

}
