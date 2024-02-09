package com.hackathon.tohanoapimvn.endpoint.dto;

import java.time.ZonedDateTime;
import java.util.List;

public record UserDto(Long id, String username, String name, String email, String role) {
}