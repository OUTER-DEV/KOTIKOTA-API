package com.hackathon.tohanoapimvn.service;


import com.hackathon.tohanoapimvn.model.Message;
import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public List<Message> getMessagesByUser(Optional<User> user) {
        return messageRepository.findBySenderOrReceiver(user, user);
    }

    public Message sendMessage(User sender, User receiver, String content) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        return messageRepository.save(message);
    }
}
