package com.hackathon.tohanoapimvn.repository;

import com.hackathon.tohanoapimvn.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
