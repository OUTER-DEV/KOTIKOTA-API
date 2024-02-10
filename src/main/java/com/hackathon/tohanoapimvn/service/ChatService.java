package com.hackathon.tohanoapimvn.service;

import com.hackathon.tohanoapimvn.endpoint.dto.ChatDTO;
import com.hackathon.tohanoapimvn.endpoint.rest.mapper.ChatMapper;
import com.hackathon.tohanoapimvn.model.Chat;
import com.hackathon.tohanoapimvn.model.Message;
import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.model.exception.ChatNotFoundException;
import com.hackathon.tohanoapimvn.model.exception.UserNotFoundException;
import com.hackathon.tohanoapimvn.repository.ChatRepository;
import com.hackathon.tohanoapimvn.repository.UserRepository;
import com.hackathon.tohanoapimvn.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.hackathon.tohanoapimvn.endpoint.dto.MessageDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ChatService {


  private ChatRepository chatRepository;
  private UserRepository userRepository;

  public Chat openChatWithUser(User currentUser, User otherUser) {
    // Vérifier si un chat existe déjà entre ces deux utilisateurs
    Chat existingChat = chatRepository.findByParticipants(currentUser, otherUser);
    if (existingChat != null) {
      return existingChat;
    } else {
      // Créer un nouveau chat avec ces deux utilisateurs
      Chat newChat = new Chat();
      newChat.addParticipant(currentUser);
      newChat.addParticipant(otherUser);
      return chatRepository.save(newChat);
    }
  }

  public List<Chat> getChatsForUser(User user) {
    // Récupérer la liste des chats dans lesquels l'utilisateur figure
    return chatRepository.findByParticipantsContaining(user);
  }
}
