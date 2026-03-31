package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateAssignmentException extends RuntimeException {

	public DuplicateAssignmentException(Long projectId, Long employeeId) {
		super("Employee " + employeeId + " is already assigned to project " + projectId);
	}
}
