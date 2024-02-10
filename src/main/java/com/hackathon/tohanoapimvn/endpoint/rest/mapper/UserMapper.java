package com.hackathon.tohanoapimvn.endpoint.rest.mapper;


import com.hackathon.tohanoapimvn.endpoint.dto.SignupRequest;
import com.hackathon.tohanoapimvn.endpoint.dto.UserDto;
import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.security.WebConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.hackathon.tohanoapimvn.endpoint.dto.UserDto;
import com.hackathon.tohanoapimvn.model.User;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserMapper {

    @Autowired
    private final PasswordEncoder passwordEncoder;



    public UserDto toDomain(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getRole());
    }

    public User mapSignUpRequestToUser(SignupRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(WebConfig.USER);
        return user;
    }
    public static UserDto toDto(User user) {
        return new UserDto(
          user.getId(),
          user.getUsername(),
          user.getName(),
          user.getEmail(),
          user.getRole()
        );
    }
}


