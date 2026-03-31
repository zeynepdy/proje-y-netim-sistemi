package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
	name = "project_assignments",
	uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "employee_id"})
)
public class ProjectAssignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "project_id", nullable = false)
	@NotNull(message = "Project is required")
	private Project project;

	@ManyToOne(optional = false)
	@JoinColumn(name = "employee_id", nullable = false)
	@NotNull(message = "Employee is required")
	private Employee employee;

	private LocalDateTime assignedAt;

	public ProjectAssignment() {
	}

	@PrePersist
	public void prePersist() {
		if (assignedAt == null) {
			assignedAt = LocalDateTime.now();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDateTime getAssignedAt() {
		return assignedAt;
	}

	public void setAssignedAt(LocalDateTime assignedAt) {
		this.assignedAt = assignedAt;
	}
}
