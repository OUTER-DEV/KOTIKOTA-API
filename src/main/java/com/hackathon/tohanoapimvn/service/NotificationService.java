package com.hackathon.tohanoapimvn.service;


import com.hackathon.tohanoapimvn.model.Notification;
import com.hackathon.tohanoapimvn.model.Project;
import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.model.exception.ProjectNotFoundException;
import com.hackathon.tohanoapimvn.model.exception.UserNotFoundException;
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
  public void addNotificationAsync(Long ownerId, Long receiverId) {
    User ownerProject = userRepository.findById(ownerId).orElseThrow(() -> new UserNotFoundException("Owner not found"));
    User donorProject = userRepository.findById(receiverId).orElseThrow(() -> new UserNotFoundException("Donor not found"));
    Project project = projectRepository.findByOwner_Id(ownerId);
    if (project == null) {
      throw new ProjectNotFoundException("Project not found for owner ID: " + ownerId);
    }
    StringBuilder messagetoNotify = new StringBuilder();
    messagetoNotify.append(ownerProject.getName()).append(" !! ,l'utilisateur ").append(donorProject.getName()).append(" est intéressé(e) par votre projet, veuillez lui joindre en message privé pour une opportunité");

    Notification notification = new Notification();
    notification.setProject(project);
    notification.setContent(messagetoNotify.toString());
    notification.setUser(ownerProject);
    notificationRepository.save(notification);
  }

}
