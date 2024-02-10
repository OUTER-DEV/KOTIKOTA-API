package com.hackathon.tohanoapimvn.repository;

import com.hackathon.tohanoapimvn.model.Message;
import com.hackathon.tohanoapimvn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderOrReceiver(Optional<User> sender, Optional<User> receiver);
}
