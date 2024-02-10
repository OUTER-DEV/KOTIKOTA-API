package com.hackathon.tohanoapimvn.endpoint.rest.controller;

import com.hackathon.tohanoapimvn.endpoint.dto.ContributionDto;
import com.hackathon.tohanoapimvn.endpoint.dto.ProjectCreateDto;
import com.hackathon.tohanoapimvn.endpoint.dto.ProjectDto;
import com.hackathon.tohanoapimvn.endpoint.dto.ProjectUpdateDto;
import com.hackathon.tohanoapimvn.endpoint.mapper.ProjectMapper;
import com.hackathon.tohanoapimvn.model.PredictionExplanation;
import com.hackathon.tohanoapimvn.model.Project;
import com.hackathon.tohanoapimvn.model.exception.ProjectNotFoundException;
import com.hackathon.tohanoapimvn.model.exception.UserNotFoundException;
import com.hackathon.tohanoapimvn.service.NotificationService;
import com.hackathon.tohanoapimvn.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Projects")
@AllArgsConstructor
@EnableAsync
public class ProjectController {

  private final ProjectService projectService;
  private final NotificationService notificationService;
  private final ProjectMapper projectMapper;


  @GetMapping("/all")
  public List<ProjectDto> getAllProjects() {
    return projectService.getAllProjects().stream().map(projectMapper::toDomain).collect(Collectors.toList());
  }

  @GetMapping("/{projectId}")
  public ProjectDto getProjectById(@PathVariable Long projectId) {
    Project project = projectService.getProjectById(projectId);
    if (project == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id: " + projectId);
    }
    return projectMapper.toDomain(project);
  }

  @PostMapping("/launch")
  public ResponseEntity<ProjectDto> launchProject(@RequestBody ProjectCreateDto request) {
    Project project = projectService.launchProject(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(projectMapper.toDomainWithoutContributors(project));
  }

  @PostMapping("/{projectId}/contribute")
  public ResponseEntity<String> contributeToProject(@PathVariable Long projectId, @RequestBody ContributionDto request) {
    request.setProjectId(projectId);
    try {
      notificationService.addNotificationAsync(request.getOwnerId(),request.getDonorId());
      projectService.contributeToProject(request);
      return ResponseEntity.ok("Contribution successful");
    } catch (ProjectNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    } catch (UserNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    } catch (ResponseStatusException e) {
      throw e;
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error");
    }
  }

  @PutMapping("/{projectId}/update")
  public ResponseEntity<ProjectDto> updateProject(@PathVariable Long projectId, @RequestBody ProjectUpdateDto request) {
    Project updatedProject = projectService.updateProject(projectId, request);
    return ResponseEntity.ok(projectMapper.toDomain(updatedProject) );
  }

  @GetMapping("/{projectId}/predict")
  public ResponseEntity<PredictionExplanation> predictSuccessProbability(@PathVariable Long projectId) {
    // Appel du service pour prédire la probabilité de succès du projet
    double successProbability = projectService.predictSuccessProbability(projectId);

    // Génération de l'explication
    PredictionExplanation explanation = projectService.generateExplanation(successProbability);

    // Retour de la réponse avec la prédiction et les explications
    return ResponseEntity.ok(explanation);
  }


}
