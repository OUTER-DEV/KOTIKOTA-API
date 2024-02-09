package com.hackathon.tohanoapimvn.endpoint.dto;

import lombok.Data;


@Data
public class NotificationResponse {
    private Long id;
    private Long ownerId;
    private Long projectId;
    private String content;
}
