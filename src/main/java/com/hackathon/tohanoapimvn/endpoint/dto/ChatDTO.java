package com.hackathon.tohanoapimvn.endpoint.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChatDTO {
  private Long id;
  private String firstUserName;
  private String secondUserName;
  private List<MessageDTO> messageList;
}
