package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmployeeEmailException extends RuntimeException {

	public DuplicateEmployeeEmailException(String email) {
		super("Employee email already exists: " + email);
	}
}
