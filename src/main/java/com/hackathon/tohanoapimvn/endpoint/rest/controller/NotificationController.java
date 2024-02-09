package com.hackathon.tohanoapimvn.endpoint.rest.controller;


import com.hackathon.tohanoapimvn.endpoint.dto.NotificationDto;
import com.hackathon.tohanoapimvn.endpoint.mapper.NotificationMapper;
import com.hackathon.tohanoapimvn.model.Notification;
import com.hackathon.tohanoapimvn.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    private final NotificationMapper notificationMapper;

    @GetMapping("/notification/{userId}")
    private List<NotificationDto> getAllUserNotification(@PathVariable Long userId){
        return notificationService.getAllNotificationByUser(userId).stream().map(notificationMapper::toDomain).collect(Collectors.toList());
    }
}
