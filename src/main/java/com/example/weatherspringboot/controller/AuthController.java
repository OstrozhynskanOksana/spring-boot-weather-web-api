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


    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(@Valid @RequestBody UsersDataDto usersData) {
        log.info("User is registered with email: {}", usersData.getEmail());
        String token = authService.saveUserData(usersData);
        return ResponseEntity.ok(new AuthResponseDto(token));


    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto> signin(@Valid @RequestBody LoginRequestDto loginRequest) {
        String token = authService.loginUser(loginRequest);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }


}
