package com.hackathon.tohanoapimvn.endpoint.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class MessageDTO {
  private Long id;
  private String senderUserName;
  private Timestamp timestamp;
  private String replyMessage;
}
