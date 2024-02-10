package com.hackathon.tohanoapimvn.endpoint.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;


@Builder
public record UserDto(Long id, String username, String name, String email, String role) {
}

