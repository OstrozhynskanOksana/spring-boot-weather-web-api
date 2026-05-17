package com.example.weatherspringboot.service;

import com.example.weatherspringboot.dto.LoginRequestDto;
import com.example.weatherspringboot.dto.UsersDataDto;
import com.example.weatherspringboot.entity.UserEntity;
import com.example.weatherspringboot.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;


    public String register(UsersDataDto usersData) {
        UserEntity user;
        try {
            user = userService.register(usersData);
        } catch (EmailAlreadyExistsException exception) {
            user = userService.findByEmail(usersData.getEmail());
            if (!userService.passwordMatches(user, usersData.getPassword())) {
                throw exception;
            }
        }
        List<String> roles = List.of(user.getRole().name());
        return jwtService.generateJwtToken(user.getEmail(), roles);
    }

    public String loginUser(LoginRequestDto loginRequest) {
        UserEntity user = userService.findByEmail(loginRequest.getEmail());
        if (!userService.passwordMatches(user, loginRequest.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return jwtService.generateJwtToken(user.getEmail(), List.of(user.getRole().name()));
    }

}
