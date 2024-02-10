package com.hackathon.tohanoapimvn.endpoint.rest.controller;

import com.hackathon.tohanoapimvn.model.Chat;
import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.repository.ChatRepository;
import com.hackathon.tohanoapimvn.repository.UserRepository;
import com.hackathon.tohanoapimvn.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessageController {

  @Autowired
  private MessageService messageService;
  private UserRepository userRepository;
  private ChatRepository chatRepository;

  @PostMapping("/send")
  public ResponseEntity<Void> sendMessage(@RequestParam Long senderId, @RequestParam Long chatId, @RequestParam String content) throws ChangeSetPersister.NotFoundException, ChangeSetPersister.NotFoundException {
    User sender = userRepository.findById(senderId)
      .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    Chat chat = chatRepository.findById(chatId)
      .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    messageService.sendMessage(sender, chat, content);
    return ResponseEntity.ok().build();
  }
}
