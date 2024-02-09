package com.hackathon.tohanoapimvn.service;

import com.hackathon.tohanoapimvn.endpoint.dto.ContributionDto;
import com.hackathon.tohanoapimvn.endpoint.dto.ProjectCreateDto;
import com.hackathon.tohanoapimvn.endpoint.dto.ProjectUpdateDto;
import com.hackathon.tohanoapimvn.model.Project;
import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.model.exception.ProjectNotFoundException;
import com.hackathon.tohanoapimvn.model.exception.UserNotFoundException;
import com.hackathon.tohanoapimvn.repository.ProjectRepository;
import com.hackathon.tohanoapimvn.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
@EnableAsync
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
    project.setCategory(request.getCategory());


    User owner = userRepository.findById(request.getOwnerId())
      .orElseThrow(() -> new UserNotFoundException("Owner not found with ID: " + request.getOwnerId()));

    project.setOwner(owner);

    return projectRepository.save(project);
  }

  public void contributeToProject(ContributionDto request) {
    Project project = projectRepository.findById(request.getProjectId())
      .orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + request.getProjectId()));

    User donor = userRepository.findById(request.getDonorId())
      .orElseThrow(() -> new UserNotFoundException("Donor not found with ID: " + request.getDonorId()));

    // Vérifie si le propriétaire du projet est le donneur
    if (project.getOwner().getId().equals(request.getDonorId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner cannot contribute to their own project");
    }

    // Vérifie si le donneur est déjà dans la liste des contributeurs
    boolean isNewDonor = !project.getContributors().contains(donor);

    // Met à jour le solde du projet
    project.setBalance(project.getBalance() + request.getAmount());

    // Met à jour le pourcentage atteint
    double percentageAchieved = (project.getBalance() / project.getTargetAmount()) * 100;
    project.setPercentageAchieved(percentageAchieved);

    // Si le donneur est un nouveau contributeur
    if (isNewDonor) {
      // Ajoute le donneur à la liste des contributeurs
      project.getContributors().add(donor);
      // Met à jour le nombre total de contributeurs
      project.setNumberOfContributors(project.getNumberOfContributors() + 1);
    }

    projectRepository.save(project);
  }

  public Project updateProject(Long projectId, ProjectUpdateDto request) {
    Project existingProject = projectRepository.findById(projectId)
      .orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + projectId));

    if (request.getTitle() != null) {
      existingProject.setTitle(request.getTitle());
    }
    if (request.getDescription() != null) {
      existingProject.setDescription(request.getDescription());
    }
    if (request.getTargetAmount() != null) {
      existingProject.setTargetAmount(request.getTargetAmount());
    }
    if (request.getDeadline() != null) {
      existingProject.setDeadline(request.getDeadline());
    }
    if (request.getCategory() != null) {
      existingProject.setCategory(request.getCategory());
    }

    // Enregistrer les modifications
    return projectRepository.save(existingProject);
  }


}
