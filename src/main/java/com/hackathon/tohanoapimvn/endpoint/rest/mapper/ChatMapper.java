package com.hackathon.tohanoapimvn.endpoint.rest.mapper;

import com.hackathon.tohanoapimvn.endpoint.dto.ChatDTO;
import com.hackathon.tohanoapimvn.endpoint.dto.MessageDTO;
import com.hackathon.tohanoapimvn.model.Chat;
import com.hackathon.tohanoapimvn.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatMapper {

  @Autowired
  private MessageMapper messageMapper;

  public ChatDTO mapToChatDTO(Chat chat) {
    ChatDTO chatDTO = new ChatDTO();
    chatDTO.setId(chat.getId());
    chatDTO.setFirstUserName(chat.getFirstUser().getUsername());
    chatDTO.setSecondUserName(chat.getSecondUser().getUsername());
    chatDTO.setMessageList(messageMapper.mapToMessageDTOList(chat.getMessageList()));
    return chatDTO;
  }
}

