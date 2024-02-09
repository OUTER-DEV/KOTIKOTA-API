package com.hackathon.tohanoapimvn.endpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContributionDto {
  private Long donorId;
  private Long ownerId;
  private Long projectId;
  private double amount;
}

