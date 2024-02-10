package com.hackathon.tohanoapimvn.endpoint.rest.mapper;

import com.hackathon.tohanoapimvn.endpoint.dto.MessageDTO;
import com.hackathon.tohanoapimvn.model.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageMapper {

  public MessageDTO mapToMessageDTO(Message message) {
    MessageDTO messageDTO = new MessageDTO();
    messageDTO.setId(message.getId());
    messageDTO.setSenderUserName(message.getSenderUserName());
    messageDTO.setTimestamp(message.getTimestamp());
    messageDTO.setReplyMessage(message.getReplyMessage());
    return messageDTO;
  }

  public List<MessageDTO> mapToMessageDTOList(List<Message> messageList) {
    return messageList.stream()
      .map(this::mapToMessageDTO)
      .collect(Collectors.toList());
  }
}
