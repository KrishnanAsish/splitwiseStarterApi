package com.splitwise.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.splitwise.validator.impl.UserRegistorValidatorImpl;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserRegistorValidatorImpl.class)
public @interface UserRegisterValidator {

	String message() default "Both user name and phone number cannot be empty";
	
	Class<?>[] groups() default {};
	 
    Class<? extends Payload>[] payload() default {};
}
