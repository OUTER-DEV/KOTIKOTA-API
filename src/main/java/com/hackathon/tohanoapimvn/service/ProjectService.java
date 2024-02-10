package com.hackathon.tohanoapimvn.service;

import com.hackathon.tohanoapimvn.endpoint.dto.ContributionDto;
import com.hackathon.tohanoapimvn.endpoint.dto.ProjectCreateDto;
import com.hackathon.tohanoapimvn.endpoint.dto.ProjectUpdateDto;
import com.hackathon.tohanoapimvn.model.PredictionExplanation;
import com.hackathon.tohanoapimvn.model.Project;
import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.model.exception.ProjectNotFoundException;
import com.hackathon.tohanoapimvn.model.exception.UserNotFoundException;
import com.hackathon.tohanoapimvn.repository.ProjectRepository;
import com.hackathon.tohanoapimvn.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


import org.apache.commons.math3.analysis.function.Sigmoid;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;

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

  public double predictSuccessProbability(Long projectId) {
    // Vérifier si le projet existe dans la base de données
    if (!projectRepository.existsById(projectId)) {
      throw new ProjectNotFoundException("Project not found with ID: " + projectId);
    }

    // Obtenir les détails du projet depuis le référentiel
    Project project = projectRepository.findById(projectId).get();

    // Exemple de caractéristiques fictives (à remplacer par des données réelles)
    double targetAmount = project.getTargetAmount();
    int numberOfContributors = project.getNumberOfContributors();
    double percentageAchieved = project.getPercentageAchieved();

    // Récupérer le taux de réussite global de la catégorie de projet
    double categorySuccessRate = getCategorySuccessRate(project.getCategory());

    // Utiliser une formule simple pour évaluer la probabilité de succès en fonction de certaines caractéristiques du projet
    double successProbability = generateProbability(targetAmount, numberOfContributors, percentageAchieved, categorySuccessRate);

    return successProbability;
  }

  // Méthode pour récupérer le taux de réussite global de la catégorie de projet

  public double getCategorySuccessRate(String category) {
    // Récupérer tous les projets de la catégorie donnée
    List<Project> projectsInCategory = projectRepository.findAllByCategory(category);

    // Si aucun projet n'est trouvé dans la catégorie, retourner un taux de succès de 0
    if (projectsInCategory.isEmpty()) {
      return 0.0;
    }

    // Calculer la somme des pourcentages atteints de tous les projets dans la catégorie
    double totalPercentageAchieved = 0;
    for (Project project : projectsInCategory) {
      totalPercentageAchieved += project.getPercentageAchieved();
    }

    // Calculer le taux de réussite global de la catégorie en divisant la somme des pourcentages par le nombre de projets
    double categorySuccessRate = totalPercentageAchieved / projectsInCategory.size();

    return categorySuccessRate;
  }

  private double generateProbability(double targetAmount, int numberOfContributors, double percentageAchieved, double categorySuccessRate) {
    // Si le nombre de contributeurs est zéro, on peut considérer que le projet est au stade initial
    // Dans ce cas, on peut affecter un poids plus faible à cette caractéristique pour refléter le niveau d'incertitude
    double contributorWeight = (numberOfContributors > 0) ? 0.2 : 0.05;

    // Ajout du taux de réussite global de la catégorie comme une caractéristique supplémentaire
    double[] features = {targetAmount, numberOfContributors, percentageAchieved, categorySuccessRate};
    double[] weights = {0.1, contributorWeight, 0.3, 0.2}; // Poids arbitraires, à ajuster avec un modèle entraîné

    // Calculer le score linéaire en multipliant les caractéristiques par les poids
    double linearScore = 0;
    for (int i = 0; i < features.length; i++) {
      linearScore += features[i] * weights[i];
    }

    // Appliquer la fonction de lien logistique pour obtenir la probabilité de succès
    Sigmoid sigmoid = new Sigmoid();
    double successProbability = sigmoid.value(linearScore);

    return successProbability;
  }

  public PredictionExplanation generateExplanation(double successProbability) {
    StringBuilder explanation = new StringBuilder();
    String additionalExplanation = "";

    if (successProbability >= 0.8) {
      explanation.append("According to our predictive analysis, this project has a high probability of success. ");
      explanation.append("This conclusion is drawn because the project has already reached a significant percentage of its target amount, ");
      explanation.append("has a large number of contributors, and belongs to a category with historically successful projects.");

      // Ajout d'une explication supplémentaire pour les projets ayant une probabilité de succès élevée
      additionalExplanation = "The project's strong performance in key metrics indicates a high level of community interest and support.";
    } else if (successProbability >= 0.5) {
      explanation.append("Our predictive model suggests that this project has a moderate probability of success. ");
      explanation.append("This conclusion is based on the project's progress towards its target amount and the number of contributors, ");
      explanation.append("which indicate reasonable interest and support from the community.");

      // Ajout d'une explication supplémentaire pour les projets ayant une probabilité de succès modérée
      additionalExplanation = "While the project shows promise, there is room for improvement in community engagement to enhance its chances of success.";
    } else {
      explanation.append("Based on our predictive analysis, this project may face challenges in achieving success. ");
      explanation.append("This is primarily due to the project's slow progress towards its target amount ");
      explanation.append("and limited engagement from the community so far. Additional outreach efforts may be necessary to attract more contributors.");

      // Ajout d'une explication supplémentaire pour les projets ayant une probabilité de succès faible
      additionalExplanation = "The project's current metrics suggest a need for significant community outreach and marketing efforts to increase its visibility and attract more support.";
    }

    // Création de l'objet PredictionExplanation avec l'explication supplémentaire
    PredictionExplanation predictionExplanation = new PredictionExplanation(successProbability, explanation.toString(), additionalExplanation);

    return predictionExplanation;
  }


}
