package com.splitwise.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanUtils;

import com.splitwise.validator.UserRegisterValidator;
import com.splitwise.vo.UserBasicVO;

public class UserRegistorValidatorImpl implements ConstraintValidator<UserRegisterValidator, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		UserBasicVO userBasicVo = new UserBasicVO();
		BeanUtils.copyProperties(value, userBasicVo);
		if(value instanceof UserBasicVO) {
			if(userBasicVo.getEmailId().isEmpty() && userBasicVo.getPhoneNo().isEmpty())
				return false;
			else return true;
		}else return false;
	}

}
