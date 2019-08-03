package org.myopenproject.esamu.data.dao;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

public class Validator {
	private static final javax.validation.Validator validator;
	
	private Validator() {}
	
	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		factory.close();
	}
	
	public static <T> Set<ConstraintViolation<T>> validate(T obj) {
		return validator.validate(obj);
	}
}
