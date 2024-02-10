package com.hackathon.tohanoapimvn.endpoint.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDtoNoName {
    private Long id;
    private String username;
}
