package com.hackathon.tohanoapimvn.service;


import com.hackathon.tohanoapimvn.model.Chat;
import com.hackathon.tohanoapimvn.model.Message;
import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.repository.ChatRepository;
import com.hackathon.tohanoapimvn.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private ChatRepository chatRepository;

    public void sendMessage(User sender, Chat chat, String content) {
        // Créer un nouveau message
        Message message = new Message();
        message.setSender(sender);
        message.setChat(chat);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        // Ajouter le message à la liste des messages du chat
        chat.getMessages().add(message);
        // Enregistrer le chat (et implicitement le message en cascade)
        chatRepository.save(chat);
    }
}

