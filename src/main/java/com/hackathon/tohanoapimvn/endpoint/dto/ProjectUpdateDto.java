package com.hackathon.tohanoapimvn.endpoint.dto;

import lombok.Data;

@Data
public class ProjectUpdateDto {
  private String title;
  private String description;
  private Double targetAmount;
  private String deadline;
  private String category;
  private String projectLink;
}
