package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.request.ProjectAssignmentRequest;
import com.example.demo.entity.ProjectAssignment;
import com.example.demo.service.ProjectAssignmentService;

@RestController
@RequestMapping("/api/assignments")
public class ProjectAssignmentController {

	private final ProjectAssignmentService projectAssignmentService;

	public ProjectAssignmentController(ProjectAssignmentService projectAssignmentService) {
		this.projectAssignmentService = projectAssignmentService;
	}

	@PostMapping
	public ResponseEntity<ProjectAssignment> assignEmployeeToProject(
		@Valid @RequestBody ProjectAssignmentRequest request
	) {
		ProjectAssignment createdAssignment = projectAssignmentService.assignEmployeeToProject(
			request.getProjectId(),
			request.getEmployeeId()
		);

		return ResponseEntity
			.created(URI.create("/api/assignments/" + createdAssignment.getId()))
			.body(createdAssignment);
	}

	@GetMapping("/project/{projectId}")
	public ResponseEntity<List<ProjectAssignment>> getAssignmentsByProjectId(@PathVariable Long projectId) {
		return ResponseEntity.ok(projectAssignmentService.getAssignmentsByProjectId(projectId));
	}

	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<List<ProjectAssignment>> getAssignmentsByEmployeeId(@PathVariable Long employeeId) {
		return ResponseEntity.ok(projectAssignmentService.getAssignmentsByEmployeeId(employeeId));
	}

	@DeleteMapping("/{assignmentId}")
	public ResponseEntity<Void> deleteAssignment(@PathVariable Long assignmentId) {
		projectAssignmentService.deleteAssignment(assignmentId);
		return ResponseEntity.noContent().build();
	}
}
