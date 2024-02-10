package com.hackathon.tohanoapimvn.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String projectImage;

  private String description;

  private String category;

  private double targetAmount;

  private double balance;

  private String deadline;

  private double percentageAchieved;

  private int numberOfContributors;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  @ManyToMany
  @JoinTable(
    name = "project_contributors",
    joinColumns = @JoinColumn(name = "project_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<User> contributors;

}
