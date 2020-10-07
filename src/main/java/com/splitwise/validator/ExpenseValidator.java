package com.splitwise.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.splitwise.validator.impl.ExpenseValidatorImpl;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExpenseValidatorImpl.class)
public @interface ExpenseValidator {

	String message() default "Invalid expense data";
	
	Class<?>[] groups() default {};
	 
    Class<? extends Payload>[] payload() default {};
}
