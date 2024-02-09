package com.hackathon.tohanoapimvn.service;

import com.hackathon.tohanoapimvn.model.Project;
import com.hackathon.tohanoapimvn.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@EnableAsync
public class ProjectService {

  private final ProjectRepository projectRepository;

  public List<Project> getAllProjects() {
    return projectRepository.findAll();
  }
}
