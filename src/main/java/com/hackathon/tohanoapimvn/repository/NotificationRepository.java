package com.hackathon.tohanoapimvn.repository;

import com.hackathon.tohanoapimvn.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@EnableJpaRepositories
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findAllByUser_Id(Long id);
}
