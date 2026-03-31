package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Project;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.repository.ProjectRepository;

@Service
public class ProjectService {

	private final ProjectRepository projectRepository;

	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	public Project getProjectById(Long id) {
		return projectRepository.findById(id)
			.orElseThrow(() -> new ProjectNotFoundException(id));
	}

	public Project createProject(Project project) {
		return projectRepository.save(project);
	}

	public Project updateProject(Long id, Project updatedProject) {
		Project existingProject = getProjectById(id);
		existingProject.setName(updatedProject.getName());
		existingProject.setDescription(updatedProject.getDescription());
		existingProject.setStartDate(updatedProject.getStartDate());
		existingProject.setEndDate(updatedProject.getEndDate());
		existingProject.setStatus(updatedProject.getStatus());
		return projectRepository.save(existingProject);
	}

	public void deleteProject(Long id) {
		Project project = getProjectById(id);
		projectRepository.delete(project);
	}
}
