package com.hackathon.tohanoapimvn.endpoint.mapper;


import com.hackathon.tohanoapimvn.endpoint.dto.ProjectDto;
import com.hackathon.tohanoapimvn.model.Project;
import com.hackathon.tohanoapimvn.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {


    public ProjectDto toDomain(Project project){

        return  ProjectDto.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .category(project.getCategory())
                .balance(project.getBalance())
                .deadline(project.getDeadline())
                .percentageAchieved(project.getPercentageAchieved())
                .numberOfContributors(project.getNumberOfContributors())
                .owner(project.getOwner().getName())
                .contributors(getContributorsOrEmptyList(project))
                .build();
    }

    public ProjectDto toDomainWithoutContributors(Project project){

        return  ProjectDto.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .category(project.getCategory())
                .balance(project.getBalance())
                .deadline(project.getDeadline())
                .percentageAchieved(project.getPercentageAchieved())
                .numberOfContributors(project.getNumberOfContributors())
                .owner(project.getOwner().getName())
                .build();
    }

    public static List<String> getContributorsOrEmptyList(Project project) {
        List<User> contributors = project.getContributors();
        if (contributors.isEmpty()) {
            return List.of();
        } else {
            return contributors.stream()
                    .map(User::getName)
                    .collect(Collectors.toList());
        }
    }
}
