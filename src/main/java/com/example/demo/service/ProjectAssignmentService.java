package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Project;
import com.example.demo.entity.ProjectAssignment;
import com.example.demo.exception.AssignmentNotFoundException;
import com.example.demo.exception.DuplicateAssignmentException;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProjectAssignmentRepository;
import com.example.demo.repository.ProjectRepository;

@Service
public class ProjectAssignmentService {

	private final ProjectAssignmentRepository projectAssignmentRepository;
	private final ProjectRepository projectRepository;
	private final EmployeeRepository employeeRepository;

	public ProjectAssignmentService(
		ProjectAssignmentRepository projectAssignmentRepository,
		ProjectRepository projectRepository,
		EmployeeRepository employeeRepository
	) {
		this.projectAssignmentRepository = projectAssignmentRepository;
		this.projectRepository = projectRepository;
		this.employeeRepository = employeeRepository;
	}

	public ProjectAssignment assignEmployeeToProject(Long projectId, Long employeeId) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNotFoundException(projectId));
		Employee employee = employeeRepository.findById(employeeId)
			.orElseThrow(() -> new EmployeeNotFoundException(employeeId));

		if (projectAssignmentRepository.existsByProjectIdAndEmployeeId(projectId, employeeId)) {
			throw new DuplicateAssignmentException(projectId, employeeId);
		}

		ProjectAssignment assignment = new ProjectAssignment();
		assignment.setProject(project);
		assignment.setEmployee(employee);
		assignment.setAssignedAt(LocalDateTime.now());

		return projectAssignmentRepository.save(assignment);
	}

	public List<ProjectAssignment> getAssignmentsByProjectId(Long projectId) {
		if (!projectRepository.existsById(projectId)) {
			throw new ProjectNotFoundException(projectId);
		}
		return projectAssignmentRepository.findByProjectId(projectId);
	}

	public List<ProjectAssignment> getAssignmentsByEmployeeId(Long employeeId) {
		if (!employeeRepository.existsById(employeeId)) {
			throw new EmployeeNotFoundException(employeeId);
		}
		return projectAssignmentRepository.findByEmployeeId(employeeId);
	}

	public void deleteAssignment(Long assignmentId) {
		ProjectAssignment assignment = projectAssignmentRepository.findById(assignmentId)
			.orElseThrow(() -> new AssignmentNotFoundException(assignmentId));
		projectAssignmentRepository.delete(assignment);
	}
}
