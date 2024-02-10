package com.hackathon.tohanoapimvn.endpoint.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@Builder
public class ProjectDto {
    private Long id;
    private String title;
    private  String description;
    private String category;
    private double targetAmount;
    private double balance;
    private String  deadline;
    private double percentageAchieved;
    private int numberOfContributors;
    private  String owner;
    private List<String> contributors;
}
