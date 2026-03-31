package com.example.weatherspringboot.controller;

import com.example.weatherspringboot.dto.LoginRequestDto;
import com.example.weatherspringboot.dto.AuthResponseDto;
import com.example.weatherspringboot.dto.UsersDataDto;
import com.example.weatherspringboot.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> signUp(@Valid @RequestBody UsersDataDto usersData) {
        log.info("User is registered with email: {}", usersData.getEmail());
        String token = authService.register(usersData);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> signIn(@Valid @RequestBody LoginRequestDto loginRequest) {
        String token = authService.loginUser(loginRequest);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }


}
