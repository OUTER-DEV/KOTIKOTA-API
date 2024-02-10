package com.hackathon.tohanoapimvn.endpoint.rest.controller;

import com.hackathon.tohanoapimvn.endpoint.dto.ChatDTO;
import com.hackathon.tohanoapimvn.endpoint.dto.MessageDTO;
import com.hackathon.tohanoapimvn.model.Chat;
import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.repository.UserRepository;
import com.hackathon.tohanoapimvn.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chats")
@AllArgsConstructor
public class ChatController {


  private ChatService chatService;
private UserRepository userRepository;

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Chat>> getChatsForUser(@PathVariable Long userId) throws ChangeSetPersister.NotFoundException {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    List<Chat> chats = chatService.getChatsForUser(user);
    return ResponseEntity.ok(chats);
  }

  @PostMapping("/open")
  public ResponseEntity<Chat> openChatWithUser(@RequestBody Map<String, Long> requestBody) throws ChangeSetPersister.NotFoundException {
    Long currentUserId = requestBody.get("currentUserId");
    Long otherUserId = requestBody.get("otherUserId");
    User currentUser = userRepository.findById(currentUserId)
      .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    User otherUser = userRepository.findById(otherUserId)
      .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    Chat chat = chatService.openChatWithUser(currentUser, otherUser);
    return ResponseEntity.ok(chat);
  }
}
