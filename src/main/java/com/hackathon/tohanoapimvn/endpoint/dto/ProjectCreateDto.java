package com.hackathon.tohanoapimvn.endpoint.dto;

import lombok.Data;

@Data
public class ProjectCreateDto {
  private String title;
  private String description;
  private double targetAmount;
  private String deadline;
  private String category;
  private Long ownerId;
}
