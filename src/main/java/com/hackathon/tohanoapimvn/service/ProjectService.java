package com.hackathon.tohanoapimvn.service;

import com.hackathon.tohanoapimvn.endpoint.dto.ProjectCreateDto;
import com.hackathon.tohanoapimvn.model.Project;
import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.model.exception.UserNotFoundException;
import com.hackathon.tohanoapimvn.repository.ProjectRepository;
import com.hackathon.tohanoapimvn.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;


  public List<Project> getAllProjects() {
    return projectRepository.findAll();
  }

  public Project getProjectById(Long projectId) {
    return projectRepository.findById(projectId).orElse(null);
  }

  public Project launchProject(ProjectCreateDto request) {
    Project project = new Project();
    project.setTitle(request.getTitle());
    project.setDescription(request.getDescription());
    project.setTargetAmount(request.getTargetAmount());
    project.setDeadline(request.getDeadline());


    User owner = userRepository.findById(request.getOwnerId())
      .orElseThrow(() -> new UserNotFoundException("Owner not found with ID: " + request.getOwnerId()));

    project.setOwner(owner);

    return projectRepository.save(project);
  }


}
