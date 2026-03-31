package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ProjectAssignment;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Long> {

	boolean existsByProjectIdAndEmployeeId(Long projectId, Long employeeId);

	List<ProjectAssignment> findByProjectId(Long projectId);

	List<ProjectAssignment> findByEmployeeId(Long employeeId);
}
