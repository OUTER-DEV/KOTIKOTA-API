package com.hackathon.tohanoapimvn.service;


import com.hackathon.tohanoapimvn.model.Notification;
import com.hackathon.tohanoapimvn.model.Project;
import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.repository.NotificationRepository;
import com.hackathon.tohanoapimvn.repository.ProjectRepository;
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
        private final ProjectRepository projectRepository;

        public List<Notification> getAllNotificationByUser (Long userId){
            return notificationRepository.findAllByUser_Id(userId);
        }

    @Async
    public void addNotificationAsync(Long ownerId,Long receiverId) {
            User ownerProject = userRepository.findById(ownerId).get();
            User donorProject = userRepository.findById(receiverId).get();
            Project  project = projectRepository.findByOwner_Id(ownerId);
            StringBuilder messagetoNotify = new StringBuilder();
            messagetoNotify.append(ownerProject.getName()+" !! ,l'utilisateur "+donorProject.getName()+ " est intéressé(e) par votre projet," +
                    "veillez lui joindre en méssage privé pour une opportunité");

        Notification notification = new Notification();
        notification.setProject(project);
        notification.setContent(messagetoNotify.toString());
        notification.setUser(userRepository.findById(ownerId).get());
        notificationRepository.save(notification);
    }
}
