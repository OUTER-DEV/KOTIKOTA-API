package com.hackathon.tohanoapimvn.endpoint.rest.controller;


import com.hackathon.tohanoapimvn.model.Notification;
import com.hackathon.tohanoapimvn.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;


    @GetMapping("/notification/{userId}")
    private List<Notification> getAllUserNotification(@PathVariable Long userId){
        return notificationService.getAllNotificationByUser(userId);
    }
}
