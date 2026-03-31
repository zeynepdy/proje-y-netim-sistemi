package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Project;
import com.example.demo.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping
	public ResponseEntity<List<Project>> getAllProjects() {
		return ResponseEntity.ok(projectService.getAllProjects());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
		return ResponseEntity.ok(projectService.getProjectById(id));
	}

	@PostMapping
	public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
		Project createdProject = projectService.createProject(project);
		return ResponseEntity
			.created(URI.create("/api/projects/" + createdProject.getId()))
			.body(createdProject);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Project> updateProject(@PathVariable Long id, @Valid @RequestBody Project project) {
		return ResponseEntity.ok(projectService.updateProject(id, project));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
		projectService.deleteProject(id);
		return ResponseEntity.noContent().build();
	}
}
