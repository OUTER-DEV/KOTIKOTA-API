package com.hackathon.tohanoapimvn.service;


import com.hackathon.tohanoapimvn.model.Notification;
import com.hackathon.tohanoapimvn.repository.NotificationRepository;
import com.hackathon.tohanoapimvn.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
        private  final NotificationRepository notificationRepository;
        private final UserRepository userRepository;



        public List<Notification> getAllNotificationByUser (Long userId){
            return notificationRepository.findAllByUser_Id(userId);
        }

    @Async
    public void addNotificationAsync(String message,String owner,String receiver) {
            StringBuilder messagetoNotify = new StringBuilder();
            messagetoNotify.append(receiver+"!! ,l'utilisateur"+owner+ "est intéressé(e) par votre projet," +
                    ",veillez lui joindre en méssage privé pour une opportunité");

        Notification notification = new Notification();
        notification.setContent(messagetoNotify.toString());
        notification.setUser(userRepository.findByUsername(receiver).get());
        notificationRepository.save(notification);
    }
}
