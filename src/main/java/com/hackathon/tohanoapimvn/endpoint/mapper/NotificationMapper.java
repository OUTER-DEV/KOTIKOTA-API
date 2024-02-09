package com.hackathon.tohanoapimvn.endpoint.mapper;


import com.hackathon.tohanoapimvn.endpoint.dto.NotificationDto;
import com.hackathon.tohanoapimvn.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDto toDomain(Notification notification){
        return NotificationDto.builder()
                .id(notification.getId())
                .projectId(notification.getProject().getId())
                .ownerId(notification.getUser().getId())
                .content(notification.getContent())
                .build();
    }
}
