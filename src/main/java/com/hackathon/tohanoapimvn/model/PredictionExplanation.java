package com.hackathon.tohanoapimvn.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PredictionExplanation {
  private double successProbability;
  private String explanation;
  private String additionalExplanation;

}
