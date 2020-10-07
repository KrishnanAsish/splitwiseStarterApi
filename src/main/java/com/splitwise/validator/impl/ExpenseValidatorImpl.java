package com.splitwise.validator.impl;

import java.util.function.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.splitwise.utils.CommonUserUtils;
import com.splitwise.validator.ExpenseValidator;
import com.splitwise.vo.ExpenseDetailVO;
import com.splitwise.vo.ExpenseParticipantDetailVO;
import com.splitwise.vo.UserBasicVO;

public class ExpenseValidatorImpl implements ConstraintValidator<ExpenseValidator, Object> {

	@Autowired
	CommonUserUtils userUtils;
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Predicate<UserBasicVO> participantProfileValidate = p-> {
			int res = userUtils.getUserData(p);
			p.setUserId(res);
			return res!=-1;
		};
		ExpenseDetailVO expenseDetail = new ExpenseDetailVO();
		BeanUtils.copyProperties(value, expenseDetail);
		if(value instanceof ExpenseDetailVO) {
			if(CollectionUtils.isEmpty(expenseDetail.getParticipantDetails()) || expenseDetail.getTeamExpenseAmount()<=0) {
				System.out.println("Participant list cannot be empty and team expense should be > 0");
				return false;
			}
			else {
				List<ExpenseParticipantDetailVO> participDetailList = expenseDetail.getParticipantDetails();
				double totalContribFromParticipants = participDetailList.stream().map(p->p.getContribution_exact()).collect(Collectors.summingDouble(Double::doubleValue));
				if(totalContribFromParticipants!=expenseDetail.getTeamExpenseAmount()) {
					System.out.println("Participant expenses are not adding to the group expense");
					return false;
				}
				for(ExpenseParticipantDetailVO participDetail:participDetailList) {
					UserBasicVO userDetail = participDetail.getParticipantDetail();
					if(!participantProfileValidate.test(userDetail)) {
						System.out.println("User data- "+ userDetail + " is invalid");
						return false;
					}
				}
			}
			System.out.println("===========Expense data validated successfully===========");
			return true;
		}else return false;
	}

}
