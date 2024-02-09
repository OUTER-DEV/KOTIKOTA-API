package com.hackathon.tohanoapimvn.repository;

import com.hackathon.tohanoapimvn.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface MessageRepository extends JpaRepository<Message,Long> {
    Optional<Message> findById(Long id);

}
