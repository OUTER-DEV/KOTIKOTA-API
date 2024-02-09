package com.hackathon.tohanoapimvn.service;


import com.hackathon.tohanoapimvn.model.Message;
import com.hackathon.tohanoapimvn.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository repository;


}
