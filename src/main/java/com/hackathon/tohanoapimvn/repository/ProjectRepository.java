package com.hackathon.tohanoapimvn.repository;

import com.hackathon.tohanoapimvn.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByOwner_Id(Long id);
    List<Project> findAllByCategory(String category); // Ajout de la méthode pour trouver tous les projets par catégorie

}
