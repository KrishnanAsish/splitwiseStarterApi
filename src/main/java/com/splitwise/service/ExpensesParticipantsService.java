package com.splitwise.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitwise.model.AggregatesDao;
import com.splitwise.model.ExpensesParticipantsDao;
import com.splitwise.pojo.Aggregates;
import com.splitwise.pojo.Balance;
import com.splitwise.pojo.Expenses;
import com.splitwise.pojo.ExpensesParticipants;
import com.splitwise.utils.SplitWiseConstants;

@Service
public class ExpensesParticipantsService {

	@Autowired
	ExpensesParticipantsDao epDao;

	@Autowired
	AggregatesDao aggregDao;

	public List<ExpensesParticipants> findAllExpensesForUser(int id){
		return epDao.findByUserProfileId(id);
	}

	public List<ExpensesParticipants> findAllExpenses(){
		List<ExpensesParticipants> list = new ArrayList<>();
		epDao.findAll().forEach(list::add);
		return list;
	}

	public void saveExpensesParticipantDetails(List<ExpensesParticipants> expensesParticipants){
		epDao.saveAll(expensesParticipants);
	}

	//Fetch corresponding expense record
	//For each participant, calculate balance (share * total)-contribution
	public List<Balance> generateParticipantBalances(Expenses expense,
			List<ExpensesParticipants> expensesParticipants){
		List<Balance> balances = new ArrayList<>();
		int groupExpenditure = expense.getAmount(),count=0;
		String expenseType = expense.getExpenseType();
		int equilibForEqualShares = groupExpenditure/expensesParticipants.size();
		if(expenseType.equals(SplitWiseConstants.EXPENSE_TYPE_FUND_SETTLEMENT)) {
			//TODO Set share as 2 and 1 from sender?
		}
		for(ExpensesParticipants ep:expensesParticipants) {
			int participContribution = 0,participBalance=0,participEquilibrium=0;
			if(ep.isShareInPercentile())
				participContribution = groupExpenditure * ep.getContribution();
			else
				participContribution = ep.getContribution();
			if(ep.getShare()==1) { // All have equal weights
				participEquilibrium=equilibForEqualShares;
			}
			else {
				participEquilibrium=(int) (ep.getShare()*groupExpenditure);
			}
			participBalance=Math.abs(participContribution-participEquilibrium);
			if(participContribution<participEquilibrium)participBalance*=-1;
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
		Arrays.sort(balanceArray,(b1,b2)->{
			return Integer.compare(b1.getBalance(), b2.getBalance());
		});
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
				int absDiff = Math.abs(leftSideBalance.getBalance())-rightSideBalance.getBalance();
				aggregateList.add(new Aggregates(leftSideBalance.getUserId(),rightSideBalance.getUserId(),-1*rightSideBalance.getBalance()));
				aggregateList.add(new Aggregates(rightSideBalance.getUserId(),leftSideBalance.getUserId(),rightSideBalance.getBalance()));
				leftSideBalance.setBalance(-1*absDiff); // Update with the remaining value
				rightSideBalance.setBalance(0);
				rightIndex++; // Move rightIndex alone as this amount is now settled
			}else {
				int absDiff = rightSideBalance.getBalance() - Math.abs(leftSideBalance.getBalance());
				aggregateList.add(new Aggregates(leftSideBalance.getUserId(),rightSideBalance.getUserId(),leftSideBalance.getBalance()));
				aggregateList.add(new Aggregates(rightSideBalance.getUserId(),leftSideBalance.getUserId(),-1*leftSideBalance.getBalance()));
				leftSideBalance.setBalance(0);
				rightSideBalance.setBalance(absDiff);
				leftIndex--;
			}
		}
		return aggregateList;
	}

	public void genrateAndPersistAggregateData(Expenses expenseData, List<ExpensesParticipants> expensesParticipants) {
		List<Balance> balanceList = generateParticipantBalances(expenseData,expensesParticipants);
		List<Aggregates> aggregateList = generateAggregateDataForExpense(balanceList);
		aggregDao.saveAll(aggregateList);
	}
}
