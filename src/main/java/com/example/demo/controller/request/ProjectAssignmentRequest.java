package com.example.demo.controller.request;

import jakarta.validation.constraints.NotNull;

public class ProjectAssignmentRequest {

	@NotNull(message = "Project id is required")
	private Long projectId;

	@NotNull(message = "Employee id is required")
	private Long employeeId;

	public ProjectAssignmentRequest() {
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
}
