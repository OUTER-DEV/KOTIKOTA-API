package com.hackathon.tohanoapimvn.endpoint.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class NotificationDto {
    private Long id;
    private Long ownerId;
    private Long projectId;
    private String content;
}
