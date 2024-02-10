package com.hackathon.tohanoapimvn.endpoint.rest.controller;

import com.hackathon.tohanoapimvn.endpoint.dto.ContentRequest;
import com.hackathon.tohanoapimvn.model.Message;
import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.service.MessageService;
import com.hackathon.tohanoapimvn.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor

public class MessageController {

  private final MessageService messageService;
  private final  UserService userService;


  @GetMapping("/{username}")
  public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable String username) {

    Optional<User> user = userService.getUserByUsername(username);
    if (user != null) {
      List<Message> messages = messageService.getMessagesByUser(user);
      return ResponseEntity.ok(messages);
    } else {
      return ResponseEntity.notFound().build();
    }
  }


  @PostMapping("/send")
  public ResponseEntity<Message> sendMessage(@RequestParam String senderUsername, @RequestParam String receiverUsername, @RequestBody ContentRequest content) {
    User sender = userService.getUserByUsername(senderUsername).get();
    User receiver = userService.getUserByUsername(receiverUsername).get();
    if (sender != null && receiver != null) {
      Message message = messageService.sendMessage(sender, receiver, content.content());
      return ResponseEntity.ok(message);
    } else {
      return ResponseEntity.notFound().build();
    }
  }


}