package com.hackathon.tohanoapimvn.endpoint.rest.controller;

import com.hackathon.tohanoapimvn.endpoint.dto.ProjectCreateDto;
import com.hackathon.tohanoapimvn.model.Project;
import com.hackathon.tohanoapimvn.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/Projects")
@AllArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  @GetMapping("/all")
  public List<Project> getAllProjects() {
    return projectService.getAllProjects();
  }

  @GetMapping("/{projectId}")
  public Project getProjectById(@PathVariable Long projectId) {
    Project project = projectService.getProjectById(projectId);
    if (project == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id: " + projectId);
    }
    return project;
  }

  @PostMapping("/launch")
  public ResponseEntity<Project> launchProject(@RequestBody ProjectCreateDto request) {
    Project project = projectService.launchProject(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(project);
  }

}
