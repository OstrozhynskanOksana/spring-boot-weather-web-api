package com.example.weatherspringboot.controller;

import com.example.weatherspringboot.dto.UsersDataDto;
import com.example.weatherspringboot.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public void saveDataRegistration(@Valid @RequestBody UsersDataDto usersData){
        log.info("User is registered with email: {}", usersData.getEmail());
        authService.saveUserData(usersData);


    }


}
