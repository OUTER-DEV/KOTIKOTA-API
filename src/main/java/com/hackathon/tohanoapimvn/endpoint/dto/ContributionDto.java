package com.hackathon.tohanoapimvn.endpoint.dto;

import lombok.Data;

@Data
public class ContributionDto {
  private Long donorId;
  private Long ownerId;
  private Long projectId;
  private double amount;
}

