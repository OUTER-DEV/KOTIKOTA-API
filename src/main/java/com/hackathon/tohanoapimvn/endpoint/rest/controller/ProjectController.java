package com.hackathon.tohanoapimvn.endpoint.rest.controller;

import com.hackathon.tohanoapimvn.model.Project;
import com.hackathon.tohanoapimvn.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
